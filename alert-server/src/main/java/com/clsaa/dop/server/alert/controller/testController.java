package com.clsaa.dop.server.alert.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class testController {
    @GetMapping("/alert")
    public String test(){
        return "alert";
    }
}
