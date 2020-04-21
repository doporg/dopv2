package com.clsaa.dop.server.link.controller;

import com.clsaa.dop.server.link.model.vo.BindVO;
import com.clsaa.dop.server.link.service.BindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class BindController {

    // 监控流水线，可以创建、删除、停止、开始、编辑、查看列表

    @Autowired
    private BindService service;

    @GetMapping(value = "/binds")
    public List<BindVO> getBindById(@RequestParam(name = "cuser")long cUserId) {
        return service.getList(cUserId);
    }

    @PostMapping(value = "/bind/new")
    public void newBind(@RequestBody BindVO bindVO) {
        service.add(bindVO);
    }

    @DeleteMapping(value = "/bind/delete")
    public void deleteBind(@RequestParam(name = "bid")Long bid) {
        service.delete(bid);
    }

    @PutMapping(value = "/bind/modify")
    public void modifyBind(@RequestBody BindVO bindVO) {
        service.modify(bindVO);
    }

    @PostMapping(value = "/bind/stop")
    public void stopBind(@RequestParam(name = "bid")Long bid) {
        service.stop(bid);
    }

    @PostMapping(value = "/bind/start")
    public void startBind(@RequestParam(name = "bid")Long bid) {
        service.start(bid);
    }


}
