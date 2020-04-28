package com.clsaa.dop.server.link.controller;

import com.clsaa.dop.server.link.config.HttpHeaders;
import com.clsaa.dop.server.link.model.po.StarTrace;
import com.clsaa.dop.server.link.service.StarTraceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class StarController {

    @Autowired
    private StarTraceService service;

    @PostMapping(value = "/star")
    public void starTrace(@RequestHeader(HttpHeaders.X_LOGIN_USER) Long userId,
                          @RequestParam(value = "traceId")String traceId,
                          @RequestParam(value = "note", defaultValue = "")String note) {
        service.star(userId, traceId, note);
    }

    @DeleteMapping(value = "/unStar")
    public void unStarTrace(@RequestParam(value = "itemId") Long itemId) {
        service.unStar(itemId);
    }

    @GetMapping(value = "/getStars")
    public List<StarTrace> getStarList(@RequestHeader(HttpHeaders.X_LOGIN_USER)Long userId) {
        return service.getStarsByUserId(userId);
    }
}
