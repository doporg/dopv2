package com.clsaa.dop.server.baas.Service;

import com.clsaa.dop.server.baas.Mapper.ChannelMapper;
import com.clsaa.dop.server.baas.Mapper.NewNetMapper;
import com.clsaa.dop.server.baas.model.dbMo.ChannelInfo;
import com.clsaa.dop.server.baas.model.dbMo.NetInfo;
import com.clsaa.dop.server.baas.model.dbMo.NewNetInfo;
import com.clsaa.dop.server.baas.model.jsonMo.jsonModel;
import com.clsaa.dop.server.baas.model.yamlMo.BlockData;
import com.clsaa.dop.server.baas.model.yamlMo.Orderer;
import com.clsaa.dop.server.baas.model.yamlMo.TxData;
import com.clsaa.dop.server.baas.util.SFTPUtil;
import io.kubernetes.client.ApiException;
import org.apache.commons.cli.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
@Service(value = "FabricChannelService")
public class FabricChannelService {
    @Autowired
    k8sExecService k8sExecService;
    @Autowired
    NewNetMapper netMapper;
    @Autowired
    ChannelMapper channelMapper;
    @Autowired
    JsonService jsonService;
    @Autowired
    FabricK8sQueryService fabricK8sQueryService;
    @Autowired
    JenkinsService jenkinsService;
    @Autowired
    GetBlockInfoService getBlockInfoService;
    @Autowired
    K8sYamlGenerateService k8sYamlGenerateService;
    //创建channel并且把peer加进去
    //在pod里面执行shell命令 不能用kubectl exec -- command 做法是创建Linux shell 然后 kubectl exec
    //链码要在生成yaml以后 用jenkins去生成pod 并打包 不同的组织链码要不一样打包 打包以后还要得到ccid
    //invoke的话也要
    public String createChannel(int NetId,String ChannelName,List<String> peerList) throws InterruptedException, ApiException, ParseException, IOException, SftpException {
        List<String> result = new ArrayList<>();
        NewNetInfo net = netMapper.findNetById(NetId);
        String Namespace = net.getNamespace();
        List<String> con = new ArrayList<>();
        List<String> CliPodList = new ArrayList<>();
        boolean flag = true;
        for(String peer:peerList){
            con.add(peer.split("-")[1]);
        }
        Map<String,String> map = fabricK8sQueryService.getNameSpacePodListStatu(Namespace);
        Set<String> set = map.keySet();
        for(String s:set){
            for(String org:con)
                if(s.contains("cli-"+org))
                    CliPodList.add(s);
        }
        SFTPUtil sftpUtil = new SFTPUtil();
        sftpUtil.login();
        k8sYamlGenerateService.replaceChannelShell(ChannelName,2,Namespace);
        File file = new File("src/main/resources/"+Namespace+"-firstChannel.sh");
        File file2 = new File("src/main/resources/"+Namespace+"-secChannel.sh");
        InputStream is = new FileInputStream(file);
        InputStream is2 = new FileInputStream(file2);
        sftpUtil.upload("/mnt","nfsdata/fabric/"+Namespace, "firstChannel.sh",is);
        sftpUtil.upload("/mnt","nfsdata/fabric/"+Namespace, "secChannel.sh",is2);
        for(int i=0;i<CliPodList.size();i++){
            if(i==0){
                String jobName =jenkinsService.createFirstChannel(Namespace,CliPodList.get(i),ChannelName);
                jenkinsService.buildJob(jobName);
                result.add(k8sExecService.KubectlExec(CliPodList.get(i),Namespace,"peer channel list"));
            }
            else{
                String jobName =jenkinsService.createSecChannel(Namespace,CliPodList.get(i),ChannelName);
                jenkinsService.buildJob(jobName);
                result.add(k8sExecService.KubectlExec(CliPodList.get(i),Namespace,"peer channel list"));
            }
        }
        file.delete();
        file2.delete();
        sftpUtil.logout();
        for(String res:result){
            if(!res.equals(ChannelName))
                flag=false;
        }
        if(flag)
            return "create channel success!";
        else
            return "create channel fail";
    }
    /*获取区块高度*/
    public int getChannelHeight(int channelId) throws IOException, ApiException, ParseException, InterruptedException {
        ChannelInfo channelInfo = channelMapper.findChannelById(channelId);
        int NetId  = channelInfo.getNetId();
        String[] peerList =channelInfo.getPeerList().split(",");
        NewNetInfo net = netMapper.findNetById(NetId);
        String Namespace = net.getNamespace();
        Map<String,String> map = fabricK8sQueryService.getNameSpacePodListStatu(Namespace);
        Set<String> set = map.keySet();
        List<String> PeerPodList = new ArrayList<>();
        for(String s:set){
            for(String peer:peerList)
                if(s.contains(peer))
                    PeerPodList.add(s);
        }
        String cmd ="peer channel getinfo -c "+channelInfo.getChannelName();
        String result = k8sExecService.KubectlExec(PeerPodList.get(0),Namespace,cmd);
        String height = result.split(":")[2].split(",")[0];
        return Integer.parseInt(height);
    }

    /*要用到jenkins去解析*/
    public List<BlockData> QueryChannelBlock(int channelId) throws IOException, ApiException, ParseException, InterruptedException {
        ChannelInfo channelInfo = channelMapper.findChannelById(channelId);
        String channelName =channelInfo.getChannelName();
        String[] peerList =channelInfo.getPeerList().split(",");
        int NetId  = channelInfo.getNetId();
        NewNetInfo net = netMapper.findNetById(NetId);
        String Namespace = net.getNamespace();
        Map<String,String> map = fabricK8sQueryService.getNameSpacePodListStatu(Namespace);
        Set<String> set = map.keySet();
        List<String> PeerPodList = new ArrayList<>();
        for(String s:set){
            for(String peer:peerList)
                if(s.contains(peer))
                    PeerPodList.add(s);
        }
        int height = this.getChannelHeight(channelId);
        for(int i=height-1;i>=0;i--){
            String cmd ="peer channel fetch "+i+" -c "+channelName;
            k8sExecService.KubectlExec(PeerPodList.get(0),Namespace,cmd);
        }
        String jobName = jenkinsService.createBlockDataJob(Namespace,PeerPodList.get(0),height,channelName);
        jenkinsService.buildJob(jobName);
        List<BlockData> blockDataList = new ArrayList<>();
        for(int i=height-1;i>=0;i--){
            blockDataList.add(getBlockInfoService.readBlockDataFile("http://121.42.13.243/"+Namespace+"/"+channelName+"_"+i+".json"));
        }
        return blockDataList;
    }

    public List<TxData> QueryChannelTx(int channelId) throws InterruptedException, ApiException, ParseException, IOException {
        int height = this.getChannelHeight(channelId);
        ChannelInfo channelInfo = channelMapper.findChannelById(channelId);
        String channelName =channelInfo.getChannelName();
        int NetId  = channelInfo.getNetId();
        NewNetInfo net = netMapper.findNetById(NetId);
        String Namespace = net.getNamespace();
        List<TxData> dataList = new ArrayList<>();
        for(int i=height-1;i>=0;i--){
            dataList.add(getBlockInfoService.readTxDataFile("http://121.42.13.243/"+Namespace+"/"+channelName+"_"+i+".json"));
        }
        return dataList;
    }
}