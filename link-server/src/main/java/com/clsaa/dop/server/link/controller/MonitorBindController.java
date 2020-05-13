package com.clsaa.dop.server.link.controller;

import com.clsaa.dop.server.link.model.vo.monitor.BindVO;
import com.clsaa.dop.server.link.model.vo.monitor.CreateMonitorBind;
import com.clsaa.dop.server.link.model.vo.monitor.ModifyMonitorBind;
import com.clsaa.dop.server.link.model.vo.monitor.StartBindRes;
import com.clsaa.dop.server.link.service.MonitorBindService;
import com.clsaa.rest.result.Pagination;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value = "/v2/link/bind")
public class MonitorBindController {

    @Autowired
    private MonitorBindService service;

    @GetMapping()
    @ApiOperation(value = "查看监控绑定列表")
    public Pagination<BindVO> loadList(
            @ApiParam(value = "创建者Id", required = true)
            @RequestParam(value = "cuser")long cuser,
            @ApiParam(value = "页号", defaultValue = "1")
            @RequestParam(value = "pageNo", required = false, defaultValue = "1")Integer pageNo,
            @ApiParam(value = "分页大小", defaultValue = "10")
            @RequestParam(value = "pageSize", required = false, defaultValue = "10")Integer pageSize,
            @ApiParam(value = "查找关键字")
            @RequestParam(value = "keyword", required = false, defaultValue = "")String keyword) {
        System.out.println("cuser: " + cuser + ", pageNo: " + pageNo + ", pageSize: " + pageSize + ", key: " + keyword);
        return service.getPageableList(cuser, pageNo, pageSize, keyword);
    }

    @GetMapping(value = "/{bid}")
    @ApiOperation(value = "根据Id查看监控信息")
    public BindVO findBind(@ApiParam(value = "链路监控Id")@PathVariable(value = "bid")long bid) {
        System.out.println("find: " + bid);
//        BindVO vo = service.findById(bid);
//        System.out.println("find: " + vo);
//        return vo;
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return service.findById(bid);
    }

    @PostMapping()
    @ApiOperation(value = "创建链路监控与绑定")
    public void createBind(
            @ApiParam(value = "创建监控参数", required = true)
            @RequestBody CreateMonitorBind createMonitorBind) {
        System.out.println(createMonitorBind.toString());
        service.add(createMonitorBind);
    }

    @PostMapping(value = "/{bid}")
    @ApiOperation(value = "更新链路监控绑定")
    public void modifyBind(
            @ApiParam(value = "链路监控Id",required = true)@PathVariable(value = "bid")Long bid,
            @ApiParam(value = "更新监控参数", required = true)@RequestBody ModifyMonitorBind modifyMonitorBind) {
        System.out.println("update: " + modifyMonitorBind.toString());
        service.modify(modifyMonitorBind);
    }

    @PostMapping(value = "/delete/{bid}")
    @ApiOperation(value = "删除链路监控")
    public void deleteBind(
            @ApiParam(value = "链路监控Id", required = true)@PathVariable(value = "bid")Long bid) {
        service.delete(bid);
    }

    @PostMapping(value = "/start/{bid}")
    @ApiOperation(value = "启动链路监控")
    public StartBindRes startBind(
            @ApiParam(value = "链路监控Id", required = true)@PathVariable(value = "bid")Long bid) {
        return service.start(bid);
    }

    @PostMapping(value = "/stop/{bid}")
    @ApiOperation(value = "停止链路监控")
    public void stopBind(
            @ApiParam(value = "链路监控Id", required = true)@PathVariable(value = "bid")Long bid) {
        service.stop(bid);
    }

}
