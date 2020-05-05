package com.clsaa.dop.server.link.controller;

import com.clsaa.dop.server.link.enums.MonitorState;
import com.clsaa.dop.server.link.model.vo.BindVO;
import com.clsaa.dop.server.link.service.BindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@CrossOrigin
public class BindController {

    @Autowired
    private BindService service;

    @GetMapping(value = "/binds")
    public List<BindVO> getBindById(@RequestParam(name = "cuser")long cuser) {
        System.out.println("cuser: " + cuser);
        return service.getList(cuser);
    }

    @PostMapping(value = "/binds")
    public void newBind(@RequestBody BindVO bindVO) {
        System.out.println(bindVO.toString());
        service.add(bindVO);
    }

    @GetMapping(value = "/binds/{bid}")
    public void deleteBind(@PathVariable(name = "bid")Long bid) {
        System.out.println("delete bid: " + bid);
        service.delete(bid);
    }

    @PostMapping(value = "/binds/{bid}")
    public void modifyBind(@PathVariable(name = "bid")Long bid, @RequestBody BindVO bindVO) {
        service.modify(bid, bindVO);
    }

    @GetMapping(value = "/binds/{bid}/state")
    public void changeBindState(@PathVariable(name = "bid")Long bid, @RequestParam(name = "state") MonitorState state) {
        System.out.println("stop bid: " + bid);
        service.changeState(bid, state);
    }

}
