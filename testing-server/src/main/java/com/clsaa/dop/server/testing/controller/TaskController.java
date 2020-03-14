package com.clsaa.dop.server.testing.controller;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 扫描任务详细信息接口
 */
@CrossOrigin
@RestController
@RequestMapping("/api/v2")
public class TaskController {

    @GetMapping("/tasks")
    public void getAllTask(){

    }
}
