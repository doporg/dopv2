package com.clsaa.dop.server.link.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class testController {
    @GetMapping("/link")
    public String test(){
        return "link";
    }
}
