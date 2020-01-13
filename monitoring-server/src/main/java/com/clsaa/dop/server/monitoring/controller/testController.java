package com.clsaa.dop.server.monitoring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class testController {
    @GetMapping("/monitor")
    public String test(){
        return "monitor";
    }
}
