package com.clsaa.dop.server.link.controller;

import com.clsaa.dop.server.link.model.vo.BindVO;
import com.clsaa.dop.server.link.service.BindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@CrossOrigin
//        (methods = {GET, HEAD, POST, PUT, DELETE, OPTIONS}, origins = "*", allowedHeaders = "*", allowCredentials = "true")
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

    @DeleteMapping(value = "/binds/{bid}")
    public void deleteBind(@PathVariable(name = "bid")Long bid) {
        System.out.println("delete bid: " + bid);
        service.delete(bid);
    }

    @PutMapping(value = "/binds/{bid}")
    public void modifyBind(@PathVariable(name = "bid")Long bid, @RequestBody BindVO bindVO) {
        service.modify(bid, bindVO);
    }

    @PatchMapping(value = "/binds/{bid}/stop")
    public void stopBind(@PathVariable(name = "bid")Long bid) {
        System.out.println("stop bid: " + bid);
        service.stop(bid);
    }

    @PatchMapping(value = "/binds/{bid}/start")
    public void startBind(@PathVariable(name = "bid")Long bid) {
        System.out.println("start bid: " + bid);
        service.start(bid);
    }


}
