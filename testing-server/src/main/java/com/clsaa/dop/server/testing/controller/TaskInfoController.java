package com.clsaa.dop.server.testing.controller;


import com.clsaa.dop.server.testing.config.HttpHeaders;
import com.clsaa.dop.server.testing.service.TaskInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 扫描任务详细信息接口
 */
@CrossOrigin
@RestController
@RequestMapping("/api/v2/")
public class TaskInfoController {
    @Autowired
    private TaskInfoService taskInfoService;

    @GetMapping("/tasks")
    public void getAllTasks(@RequestHeader(HttpHeaders.X_LOGIN_USER) Long userId){
        taskInfoService.getAllTasks(userId);
    }


}
