package com.clsaa.dop.server.link.controller;

import com.clsaa.dop.server.link.model.vo.BindVO;
import com.clsaa.dop.server.link.service.BindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BindController {

    @Autowired
    private BindService service;

    @GetMapping(value = "/binds")
    public List<BindVO> getBindById(@RequestParam(name = "cuser")long cUserId) {
        return service.getList(cUserId);
    }

    @PostMapping(value = "/newBind")
    public void newBind(@RequestBody BindVO bindVO) {

    }
}
