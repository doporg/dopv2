package com.clsaa.dop.server.testing.controller;


import com.clsaa.dop.server.testing.config.HttpHeaders;
import com.clsaa.dop.server.testing.model.vo.TaskInfoVO;
import com.clsaa.dop.server.testing.service.TaskInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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
    public List<TaskInfoVO> getAllTasks(@RequestHeader(HttpHeaders.X_LOGIN_USER) Long userId){
        return  taskInfoService.getAllTasks(userId);
    }

    @GetMapping("/scanresult/{projectkey}")
    public void getScanResult(@RequestHeader(HttpHeaders.X_LOGIN_USER) Long userId,@PathVariable("projectkey")String projectKey){
       taskInfoService.getScanResult(projectKey);
    }

}
