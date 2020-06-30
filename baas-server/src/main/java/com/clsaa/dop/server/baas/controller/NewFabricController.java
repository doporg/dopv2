package com.baasexample.demo.controller;



import com.clsaa.dop.server.baas.Mapper.NewNetMapper;
import com.clsaa.dop.server.baas.Service.*;
import com.clsaa.dop.server.baas.model.dbMo.NewNetInfo;
import com.clsaa.dop.server.baas.model.jsonMo.jsonModel;
import com.clsaa.dop.server.baas.model.yamlMo.Orderer;
import com.clsaa.dop.server.baas.model.yamlMo.Organization;
import com.clsaa.dop.server.baas.model.yamlMo.Peer;
import com.clsaa.dop.server.baas.util.SFTPUtil;
import com.jcraft.jsch.SftpException;
import io.kubernetes.client.ApiException;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 注释写在这
 *
 * @author Monhey
 */
@CrossOrigin
@RestController
@EnableAutoConfiguration
public class NewFabricController {
    @Autowired
    JsonService jsonService;
    @Autowired
    FabricYamlGenerateService fabricYamlGenerateService;
    @Autowired
    NewNetMapper netMapper;
    @Autowired
    FabricK8sQueryService fabricK8sQueryService;
    @Autowired
    JenkinsService jenkinsService;
    @Autowired
    K8sYamlGenerateService k8sYamlGenerateService;

    @ApiOperation(value = "创建fabric网络", notes = "接口说明")
    @PostMapping("/v2/baas/createFabricNet")
    public String CreateFabric(@RequestBody String netWorkJson) throws IOException, ApiException, SftpException {
        jsonModel jm = jsonService.CastJsonToNetBean(netWorkJson);
        String NameSpace = jm.getName();
        List<Orderer> orderList = jm.getOrder();
        List<Organization> orgList = jm.getOrg();
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String current = sdf.format(date);
        NewNetInfo newNetInfo = new NewNetInfo();
        newNetInfo.setNamespace(NameSpace);
        newNetInfo.setOrdererList(orderList.toString());
        newNetInfo.setOrgList(orgList.toString());
        newNetInfo.setTls(jm.getTls());
        newNetInfo.setConsensus(jm.getConsensus());
        newNetInfo.setStatus(2);
        newNetInfo.setCreatetime(current);
        netMapper.insertNet(newNetInfo);
        fabricYamlGenerateService.replaceWithConfigtxTemplate(NameSpace,orgList,orderList,jm.getConsensus());
        fabricYamlGenerateService.replaceWithCryptoconfigTemplate(NameSpace,orderList,orgList);
        SFTPUtil sftpUtil = new SFTPUtil();
        sftpUtil.login();
        File file = new File("src/main/resources/configtx-"+NameSpace+".yaml");
        File file2 = new File("src/main/resources/crypto-config-"+NameSpace+".yaml");
        InputStream is = new FileInputStream(file);
        InputStream is2 = new FileInputStream(file2);
        sftpUtil.upload("/mnt","nfsdata/fabric/"+jm.getName(), "configtx.yaml",is);
        sftpUtil.upload("/mnt","nfsdata/fabric/"+jm.getName(), "crypto-config.yaml",is2);
        is.close();file.delete();
        is2.close();file2.delete();
        String jobName =jenkinsService.createCreatFabricJob(NameSpace);
        jenkinsService.buildJob(jobName);
        for(Orderer orderer:orderList){
            String ordererName = orderer.getOrderName();
            List<String> otherOrdererList = new ArrayList<>();
            for(Orderer o:orderList){
                if(!o.getOrderName().equals(ordererName))
                    otherOrdererList.add(o.getOrderName());
            }
            k8sYamlGenerateService.generateOrdererDeploymentYaml(ordererName,otherOrdererList,NameSpace);
            k8sYamlGenerateService.generateOrdererServiceYaml(ordererName,NameSpace);
            File ordererDepFile = new File("src/main/resources/"+ordererName+"-deployment.yaml");
            InputStream ordererDepIs = new FileInputStream(ordererDepFile);
            sftpUtil.upload("/mnt","nfsdata/fabric/"+NameSpace+"/orderer-service", ordererName+"-deployment.yaml",ordererDepIs);
            ordererDepIs.close();ordererDepFile.delete();
            File ordererSvcFile = new File("src/main/resources/"+ordererName+"-svc.yaml");
            InputStream ordererSvcIs = new FileInputStream(ordererSvcFile);
            sftpUtil.upload("/mnt","nfsdata/fabric/"+NameSpace+"/orderer-service", ordererName+"-svc.yaml",ordererSvcIs);
            ordererSvcIs.close();ordererDepFile.delete();
        }
        for(Organization org:orgList){
            List<Peer> peerList = org.getPeers();
            String orgName = org.getOrgName();
            k8sYamlGenerateService.generateCaDeploymentYaml(orgName,NameSpace);
            k8sYamlGenerateService.generateCaServiceYaml(orgName,NameSpace);
            k8sYamlGenerateService.generateCliDeploymentYaml(orgName,NameSpace);
            File caDepFile = new File("src/main/resources/"+orgName+"-ca-deployment.yaml");
            InputStream caDepIs = new FileInputStream(caDepFile);
            sftpUtil.upload("/mnt","nfsdata/fabric/"+NameSpace+"/"+orgName, orgName+"-ca-deployment.yaml",caDepIs);
            File caSvcFile = new File("src/main/resources/"+orgName+"-ca-svc.yaml");
            InputStream caSvcIs = new FileInputStream(caSvcFile);
            sftpUtil.upload("/mnt","nfsdata/fabric/"+NameSpace+"/"+orgName, orgName+"-ca-svc.yaml",caSvcIs);
            File cliDepFile = new File("src/main/resources/"+orgName+"-cli-deployment.yaml");
            InputStream cliDepIs = new FileInputStream(cliDepFile);
            sftpUtil.upload("/mnt","nfsdata/fabric/"+NameSpace+"/"+orgName, orgName+"-cli-deployment.yaml",cliDepIs);
            caDepIs.close();caDepFile.delete();
            caSvcIs.close();caSvcFile.delete();
            cliDepIs.close();cliDepFile.delete();
            for(Peer p:peerList){
                String peerName = p.getPeerName();
                k8sYamlGenerateService.generatePeerDeploymentYaml(peerName,NameSpace,orgName);
                k8sYamlGenerateService.generatePeerServiceYaml(peerName,NameSpace);
                File peerDepFile = new File("src/main/resources/"+peerName+"-deployment.yaml");
                InputStream peerDepIs= new FileInputStream(peerDepFile);
                sftpUtil.upload("/mnt","nfsdata/fabric/"+NameSpace+"/"+orgName, peerName+"-deployment.yaml",peerDepIs);
                File peerSvcFile = new File("src/main/resources/"+peerName+"-svc.yaml");
                InputStream peerSvcIs = new FileInputStream(peerSvcFile);
                sftpUtil.upload("/mnt","nfsdata/fabric/"+NameSpace+"/"+orgName, peerName+"-svc.yaml",peerSvcIs);
                peerDepIs.close();peerSvcIs.close();
                peerDepFile.delete();
                peerSvcFile.delete();
            }
        }
        k8sYamlGenerateService.generateConfigMap(NameSpace);
        File configFile = new File("src/main/resources/builders-config.yaml");
        InputStream configIs= new FileInputStream(configFile);
        sftpUtil.upload("/mnt","nfsdata/fabric/"+NameSpace+"/"+orgList.get(0).getOrgName(), "builders-config.yaml",configIs);
        configIs.close();
        configFile.delete();
        sftpUtil.logout();
        String job2 = jenkinsService.createCreatK8sFabricJob(NameSpace,orgList);
        jenkinsService.buildJob(job2);
        return "Success";
    }

    @ApiOperation(value = "根据查询fabric网络",notes = "接口说明")
    @GetMapping("/v2/baas/queryFabric/{id}")
    public String QueryFabricNetById( @PathVariable(value = "id") int id){
        NewNetInfo netInfo = netMapper.findNetById(id);
        return netInfo.toString();
    }


    @ApiOperation(value = "根据查询fabric网络",notes = "接口说明")
    @GetMapping("/v2/baas/queryFabric")
    public List<NewNetInfo> QueryFabric(){
        List<NewNetInfo> netList = netMapper.getAllNet();
        return netList;
    }
    @ApiOperation(value = "删除网络",notes = "接口说明")
    @GetMapping("/v2/baas/deleteFabric/{id}")
    public String QueryFabric(@PathVariable(value = "id")int id){
        netMapper.updateNetStatu(0,id);
        return "success";
    }
}
