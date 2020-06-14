package com.clsaa.dop.server.baas.controller;

import com.clsaa.dop.server.baas.Mapper.ChaincodeMapper;
import com.clsaa.dop.server.baas.Mapper.ChannelMapper;
import com.clsaa.dop.server.baas.Mapper.NetMapper;
import com.clsaa.dop.server.baas.Service.FabricChaincodeService;
import com.clsaa.dop.server.baas.Service.FabricK8sQueryService;
import com.clsaa.dop.server.baas.Service.JsonService;
import com.clsaa.dop.server.baas.model.dbMo.ChannelInfo;
import com.clsaa.dop.server.baas.model.dbMo.NetInfo;
import com.clsaa.dop.server.baas.model.jsonMo.chaincodeJsonModel;
import com.clsaa.dop.server.baas.model.jsonMo.jsonModel;
import io.kubernetes.client.ApiException;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    NetMapper netMapper;
    @Autowired
    ChaincodeMapper chaincodeMapper;
    @Autowired
    FabricK8sQueryService fabricK8sQueryService;

    @ApiOperation(value = "创建链码", notes = "接口说明")
    @PostMapping("/v2/baas/createChaincode")
    public String createChaincode(@RequestBody String chaincodeJson) throws IOException, ApiException {
        chaincodeJsonModel chaincodeInfo = jsonService.CastJsonToChaincodeBean(chaincodeJson);
        int NetId = chaincodeInfo.getNetworkId();
        int ChannelId = chaincodeInfo.getChannelId();
        NetInfo info = netMapper.findNetById(NetId);
        String Namespace = info.getNetName();
        jsonModel js = jsonService.CastJsonToNetBean(info.getDescription());
        String NameSpace = js.getName();
        ChannelInfo cinfo = channelMapper.findChannelById(ChannelId);
        String channelName = cinfo.getChannelName();
        String peerPodName = cinfo.getPeerList().split(",")[0];
        Map<String,String> map = fabricK8sQueryService.getNameSpacePodListStatu(Namespace);
        Set<String> set = map.keySet();
        List<String> PeerPodList = new ArrayList<>();
        for(String s:set){
            if(s.contains("cli-"))
                PeerPodList.add(s);
        }
        for(String s:set){
            if(s.contains(peerPodName))
                PeerPodList.add(s);
        }
        try {
            fabricChaincodeService.createChaincode(Namespace,PeerPodList,channelName,chaincodeInfo.getGit());}
        catch (Exception e){
            return e.toString();
        }
        return "success";
    }
}
