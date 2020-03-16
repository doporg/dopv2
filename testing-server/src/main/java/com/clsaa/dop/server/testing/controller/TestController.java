package com.clsaa.dop.server.testing.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {


    @PostMapping("/test")
    public String test(@RequestParam("sonar_token") String sonarToken){
        return sonarToken;
    }

}


