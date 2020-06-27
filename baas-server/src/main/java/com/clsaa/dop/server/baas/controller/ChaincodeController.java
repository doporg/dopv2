package com.clsaa.dop.server.baas.controller;

import com.clsaa.dop.server.baas.Mapper.ChaincodeMapper;
import com.clsaa.dop.server.baas.Mapper.ChannelMapper;
import com.clsaa.dop.server.baas.Mapper.NewNetMapper;
import com.clsaa.dop.server.baas.Service.*;
import com.clsaa.dop.server.baas.model.dbMo.ChaincodeInfo;
import com.clsaa.dop.server.baas.model.dbMo.ChannelInfo;
import com.clsaa.dop.server.baas.model.dbMo.NetInfo;
import com.clsaa.dop.server.baas.model.dbMo.NewNetInfo;
import com.clsaa.dop.server.baas.model.jsonMo.chaincodeJsonModel;
import com.clsaa.dop.server.baas.model.jsonMo.chaincodeOpInfo;
import com.clsaa.dop.server.baas.model.jsonMo.jsonModel;
import com.clsaa.dop.server.baas.util.SFTPUtil;
import com.jcraft.jsch.SftpException;
import org.apache.commons.cli.ParseException;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 注释写在这
 *
 * @author Monhey
 */
@CrossOrigin
@RestController
@EnableAutoConfiguration
public class ChaincodeController {
    @Autowired
    JsonService jsonService;
    @Autowired
    FabricChaincodeService fabricChaincodeService;
    @Autowired
    ChannelMapper channelMapper;
    @Autowired
    NewNetMapper newNetMapper;
    @Autowired
    ChaincodeMapper chaincodeMapper;
    @Autowired
    FabricK8sQueryService fabricK8sQueryService;
    @Autowired
    JenkinsService jenkinsService;
    @Autowired
    k8sExecService k8sExecService;
    @Autowired
    K8sYamlGenerateService k8sYamlGenerateService;
    @ApiOperation(value = "创建链码", notes = "接口说明")
    @PostMapping("/v2/baas/createChaincode")
    public String createChaincode(@RequestBody String chaincodeJson) throws IOException, ApiException, ParseException, InterruptedException, SftpException {
        chaincodeJsonModel chaincodeInfo = jsonService.CastJsonToChaincodeBean(chaincodeJson);
        String git = chaincodeInfo.getGit();
        int NetId = chaincodeInfo.getNetworkId();
        int ChannelId = chaincodeInfo.getChannelId();
        NewNetInfo info = newNetMapper.findNetById(NetId);
        String Namespace = info.getNamespace();
        ChannelInfo cinfo = channelMapper.findChannelById(ChannelId);
//        String channelName = cinfo.getChannelName();
        Map<String,String> map = fabricK8sQueryService.getNameSpacePodListStatu(Namespace);
        Set<String> set = map.keySet();
        String chaincodeName = chaincodeInfo.getName();
        List<String> pod1 = new ArrayList<>();
        List<String> pod2 = new ArrayList<>();
        List<String> CliPodList = new ArrayList<>();
        List<String>  peerList = new ArrayList<>();
        for(String s:set){
            if(s.contains("cli-"))
                CliPodList.add(s);
            if(s.contains("cli-org1"))
                pod1.add(s);
            if(s.contains("cli-org2"))
                pod2.add(s);
            if(s.contains("peer0-org1"))
                peerList.add(s);
        }
        if(pod1.get(0)!=null&&pod2.get(0)!=null) {
            String jobName =jenkinsService.CpInPodTarJob(pod1.get(0),pod2.get(0),Namespace,chaincodeName);
            jenkinsService.buildJob(jobName);
            String res = k8sExecService.KubectlExec(pod1.get(0),Namespace,"peer lifecycle chaincode queryinstalled");
            String ccidOrg1 = res.split("ID:")[1].split(",")[0].trim();
            String res2 = k8sExecService.KubectlExec(pod2.get(0),Namespace,"peer lifecycle chaincode queryinstalled");
            String ccidOrg2 = res2.split("ID:")[1].split(",")[0].trim();
            k8sYamlGenerateService.generateChaincodeDeploymentYaml(chaincodeName,Namespace,"org1",git,ccidOrg1);
            k8sYamlGenerateService.generateChaincodeServiceYaml(chaincodeName,Namespace,"org1");
            k8sYamlGenerateService.generateChaincodeDeploymentYaml(chaincodeName,Namespace,"org2",git,ccidOrg2);
            k8sYamlGenerateService.generateChaincodeServiceYaml(chaincodeName,Namespace,"org2");
            k8sYamlGenerateService.replaceCCidInShell1(ccidOrg1,Namespace);
            k8sYamlGenerateService.replaceCCidInShell2(ccidOrg2,Namespace);
            SFTPUtil sftpUtil = new SFTPUtil();
            sftpUtil.login();
            File file = new File("src/main/resources/chaincode-org1"+"-"+chaincodeName+"-deployment.yaml");
            File file2 = new File("src/main/resources/chaincode-org1"+"-"+chaincodeName+"-service.yaml");
            InputStream is = new FileInputStream(file);
            InputStream is2 = new FileInputStream(file2);
            sftpUtil.upload("/mnt","nfsdata/fabric/"+Namespace+"/chaincode/k8s", "chaincode-org1"+"-"+chaincodeName+"-deployment.yaml",is);
            sftpUtil.upload("/mnt","nfsdata/fabric/"+Namespace+"/chaincode/k8s", "chaincode-org1"+"-"+chaincodeName+"-service.yaml",is2);
            File file3 = new File("src/main/resources/chaincode-org2"+"-"+chaincodeName+"-deployment.yaml");
            File file4= new File("src/main/resources/chaincode-org2"+"-"+chaincodeName+"-service.yaml");
            InputStream is3 = new FileInputStream(file3);
            InputStream is4 = new FileInputStream(file4);
            sftpUtil.upload("/mnt","nfsdata/fabric/"+Namespace+"/chaincode/k8s", "chaincode-org2"+"-"+chaincodeName+"-deployment.yaml",is3);
            sftpUtil.upload("/mnt","nfsdata/fabric/"+Namespace+"/chaincode/k8s", "chaincode-org2"+"-"+chaincodeName+"-service.yaml",is4);
            File file5 = new File("src/main/resources-"+Namespace+"-chaincode"+"-org1Commit.sh");
            File file6 = new File("src/main/resources-"+Namespace+"-chaincode"+"-org2Commit.sh");
            InputStream is5 = new FileInputStream(file5);
            InputStream is6 = new FileInputStream(file6);
            sftpUtil.upload("/mnt","nfsdata/fabric/"+Namespace+"/chaincode", "org1Commit.sh",is5);
            sftpUtil.upload("/mnt","nfsdata/fabric/"+Namespace+"/chaincode", "org2Commit.sh",is6);
            File file7 = new File("src/main/resources/peerCommit.sh");
            InputStream is7 = new FileInputStream(file7);
            sftpUtil.upload("/mnt","nfsdata/fabric/"+Namespace+"/chaincode", "peerCommit.sh",is7);
            is.close();is2.close();is3.close();is4.close();is5.close();is6.close();is7.close();
            file.delete();file2.delete();file3.delete();file4.delete();file5.delete();file6.delete();file7.delete();
            String jobName2 =jenkinsService.ChaincodeOp(pod1.get(0),pod2.get(0),peerList.get(0),Namespace);
            jenkinsService.buildJob(jobName2);
            sftpUtil.logout();
            ChaincodeInfo c = new ChaincodeInfo();
            c.setChaincodeName(chaincodeName);
            c.setChaincodeVersion("v1.0");
            c.setGit(git);
            c.setChannelId(ChannelId);
            c.setNetId(NetId);
            chaincodeMapper.insertChaincode(c);
            return "success!";
        }
        return "false!";
    }

    @ApiOperation(value = "查询链码", notes = "接口说明")
    @PostMapping("/v2/baas/queryAllChaincode")
    public List<ChaincodeInfo> queryAllChaincode(){
        return chaincodeMapper.getAllChaincode();
    }

    @ApiOperation(value = "操作链码", notes = "接口说明")
    @PostMapping("/v2/baas/OperateChaincode")
    public String opetateChaincode(@RequestBody String chaincodeOpJson) throws IOException, ApiException, SftpException, ParseException, InterruptedException {
        chaincodeOpInfo chaincodeOpInfo = jsonService.CastJsonToChaincodeOp(chaincodeOpJson);
        ChaincodeInfo chaincodeInfo = chaincodeMapper.findChaincodeById(chaincodeOpInfo.getId());
        String NameSpace = newNetMapper.findNetById(chaincodeInfo.getNetId()).getNamespace();
        Map<String,String> map = fabricK8sQueryService.getNameSpacePodListStatu(NameSpace);
        StringBuilder sb = new StringBuilder();
        List<String> args = chaincodeOpInfo.getOperationParameter();
        for(int i=0;i<args.size();i++){
            if(i!=args.size()-1){
                sb.append("\""+args.get(i)+"\",");
            }
            else{
                sb.append("\""+args.get(i)+"\"");
            }
        }
        String arg =sb.toString();
        Set<String> set = map.keySet();
        List<String> cli = new ArrayList<>();
        for(String pod:set){
            if(pod.contains("cli-org1"))
                cli.add(pod);
        }
        k8sYamlGenerateService.replaceChaincodeShell(chaincodeOpInfo.getOperationName(),arg);
        SFTPUtil sftpUtil = new SFTPUtil();
        sftpUtil.login();
        File file = new File("src/main/resources/"+chaincodeOpInfo.getOperationName()+"-chaincodeOp.sh");
        InputStream is = new FileInputStream(file);
        sftpUtil.upload("/mnt","nfsdata/fabric/"+NameSpace+"/chaincode", "chaincodeOp.sh",is);
        is.close();file.delete();
        String jobName =jenkinsService.ChaincodeInvoke(cli.get(0),NameSpace,chaincodeOpInfo.getOperationName());
        jenkinsService.buildJob(jobName);
        String res = k8sExecService.KubectlExec(cli.get(0),NameSpace,"sh chaincodeOps.sh");
        return res;
    }
}

