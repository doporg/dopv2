package com.clsaa.dop.server.baas.Service;

import com.clsaa.dop.server.baas.Mapper.ChannelMapper;
import com.clsaa.dop.server.baas.Mapper.NetMapper;
import com.clsaa.dop.server.baas.model.dbMo.ChannelInfo;
import com.clsaa.dop.server.baas.model.dbMo.NetInfo;
import com.clsaa.dop.server.baas.model.jsonMo.jsonModel;
import com.clsaa.dop.server.baas.model.yamlMo.BlockData;
import com.clsaa.dop.server.baas.model.yamlMo.Orderer;
import com.clsaa.dop.server.baas.model.yamlMo.TxData;
import io.kubernetes.client.ApiException;
import org.apache.commons.cli.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
    NetMapper netMapper;
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
    //创建channel并且把peer加进去
    public List<String> createChannel(int NetId, String ChannelName, List<String> peerList) throws InterruptedException, ApiException, ParseException, IOException {
        List<String> result = new ArrayList<>();
        NetInfo net = netMapper.findNetById(NetId);
        String Namespace = net.getNetName();
        jsonModel js =jsonService.CastJsonToNetBean(net.getDescription());
        List<Orderer> ordererList = js.getOrder();
        Orderer or = ordererList.get(0);
        String order = or.getOrderName()+":"+or.getOrderport();
        StringBuilder cmd = new StringBuilder("peer channel create -o ");
        cmd.append(order+" -c");
        cmd.append(ChannelName);
        cmd.append(" -f /mnt/"+Namespace+"/scripts/channel-artifacts/channel.tx --tls true --cafile $ORDERER_CA");
        String command = cmd.toString();
        StringBuilder joinCmd = new StringBuilder("peer channel join -b ");
        joinCmd.append(ChannelName+".block");
        String joinCommand = joinCmd.toString();
        Map<String,String> map = fabricK8sQueryService.getNameSpacePodListStatu(Namespace);
        Set<String> set = map.keySet();
        List<String> PeerPodList = new ArrayList<>();
        for(String s:set){
            for(String peer:peerList)
                if(s.contains(peer))
                    PeerPodList.add(s);
        }
        StringBuilder cmd2 = new StringBuilder("peer channel fetch 0 ");
        cmd2.append(ChannelName+".block -c "+ChannelName+" -o "+order);
        cmd2.append(" --tls --cafile $ORDERER_CA");
        String joinCommand2 = cmd2.toString();
        for(int i=0;i<PeerPodList.size();i++){
            String pod = PeerPodList.get(i);
            if(i==0){
                result.add(k8sExecService.KubectlExec(pod,Namespace,command));
                result.add(k8sExecService.KubectlExec(pod,Namespace,joinCommand));
            }
            else{
                result.add((k8sExecService.KubectlExec(pod,Namespace,joinCommand2)));
                result.add(k8sExecService.KubectlExec(pod,Namespace,joinCommand));
            }
        }
        return result;
    }
    /*获取区块高度*/
    public int getChannelHeight(int channelId) throws IOException, ApiException, ParseException, InterruptedException {
        ChannelInfo channelInfo = channelMapper.findChannelById(channelId);
        int NetId  = channelInfo.getNetId();
        String[] peerList =channelInfo.getPeerList().split(",");
        NetInfo net = netMapper.findNetById(NetId);
        String Namespace = net.getNetName();
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
        NetInfo net = netMapper.findNetById(NetId);
        String Namespace = net.getNetName();
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
        NetInfo net = netMapper.findNetById(NetId);
        String Namespace = net.getNetName();
        List<TxData> dataList = new ArrayList<>();
        for(int i=height-1;i>=0;i--){
            dataList.add(getBlockInfoService.readTxDataFile("http://121.42.13.243/"+Namespace+"/"+channelName+"_"+i+".json"));
        }
        return dataList;
    }
}
