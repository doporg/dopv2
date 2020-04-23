package com.clsaa.dop.server.link.controller;

import com.clsaa.dop.server.link.config.HttpHeaders;
import com.clsaa.dop.server.link.model.vo.NoticeVO;
import com.clsaa.dop.server.link.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class NoticeController {

    @Autowired
    private NoticeService service;

    @GetMapping(value = "/notices")
    public List<NoticeVO> getListByCuser(@RequestHeader(HttpHeaders.X_LOGIN_USER) Long user) {
        return service.getListByCuser(user);
    }

}
