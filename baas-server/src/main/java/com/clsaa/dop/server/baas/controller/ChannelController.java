package com.clsaa.dop.server.baas.controller;

import com.clsaa.dop.server.baas.Mapper.ChannelMapper;
import com.clsaa.dop.server.baas.Service.FabricChannelService;
import com.clsaa.dop.server.baas.Service.JsonService;
import com.clsaa.dop.server.baas.model.dbMo.ChannelInfo;
import com.clsaa.dop.server.baas.model.jsonMo.channelJsonModel;
import com.clsaa.dop.server.baas.model.yamlMo.BlockData;
import com.clsaa.dop.server.baas.model.yamlMo.TxData;
import io.kubernetes.client.ApiException;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.cli.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * 注释写在这
 *
 * @author Monhey
 */
@CrossOrigin
@RestController
@EnableAutoConfiguration
public class ChannelController {
    @Autowired
    FabricChannelService fabricChannelService;
    @Autowired
    ChannelMapper channelMapper;
    @Autowired
    JsonService jsonService;

    @ApiOperation(value = "创建fabri通道", notes = "接口说明")
    @PostMapping("/v2/baas/createChannel")
    public List<String> CreateChannel(@RequestBody String channelJson) throws InterruptedException, ApiException, ParseException, IOException {
        channelJsonModel c = jsonService.CastJsonToChannelBean(channelJson);
        List<String> list = c.getPeers();
        StringBuilder sb = new StringBuilder("");
        for(int i=0;i<list.size();i++){
            if(i!=list.size()-1){
                sb.append(list.get(i)+",");
            }
            else{
                sb.append(list.get(i));
            }
        }
        String peerList = sb.toString();
        ChannelInfo ci = new ChannelInfo();
        ci.setChannelName(c.getName());
        ci.setNetId(Integer.parseInt(c.getNetworkId()));
        ci.setPeerList(peerList);
        channelMapper.insertChannel(ci);
        return fabricChannelService.createChannel(Integer.parseInt(c.getNetworkId()),c.getName(),list);
    }

    @ApiOperation(value = "查询fabri通道块信息", notes = "接口说明")
    @GetMapping("/v2/baas/queryBlockData/{ChannelId}")
    public List<BlockData> queryChannelBlockData(@PathVariable(value = "ChannelId") int ChannelId) throws InterruptedException, ApiException, ParseException, IOException {
        return fabricChannelService.QueryChannelBlock(ChannelId);
    }

    @ApiOperation(value = "查询fabri通道交易信息", notes = "接口说明")
    @GetMapping("/v2/baas/queryTxData/{ChannelId}")
    public List<TxData> queryChannelTXData(@PathVariable(value = "ChannelId") int ChannelId) throws InterruptedException, ApiException, ParseException, IOException {
        return fabricChannelService.QueryChannelTx(ChannelId);
    }

}