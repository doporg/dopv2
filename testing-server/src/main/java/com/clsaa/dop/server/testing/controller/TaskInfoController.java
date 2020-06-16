package com.clsaa.dop.server.testing.controller;


import com.clsaa.dop.server.testing.config.BizCodes;
import com.clsaa.dop.server.testing.config.HttpHeaders;
import com.clsaa.dop.server.testing.manage.SonarRestService;
import com.clsaa.dop.server.testing.model.vo.*;
import com.clsaa.dop.server.testing.service.TaskInfoService;
import com.clsaa.dop.server.testing.util.MyBeanUtils;
import com.clsaa.rest.result.bizassert.BizAssert;
import com.clsaa.rest.result.bizassert.BizCode;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 扫描任务详细信息接口
 */
@CrossOrigin
@RestController
@RequestMapping("/api/v2/scan")
public class TaskInfoController {
    @Autowired
    private TaskInfoService taskInfoService;
    @Autowired
    private SonarRestService sonarRestService;

    @ApiOperation(value = "根据用户id获得所有的扫描任务信息")
    @GetMapping("/tasks")
    public List<TaskInfoVO> getAllTasks(@ApiParam(value = "用户id") @RequestHeader(HttpHeaders.X_LOGIN_USER) Long userId){
        BizAssert.validParam(userId != null && userId != 0,
                new BizCode(BizCodes.INVALID_PARAM.getCode(), "用户未登录"));
        return taskInfoService.getAllTasks(userId);
    }

    @ApiOperation(value = "获得扫描任务的结果",notes = "返回扫描的结果，value=‘OK’表示静态代码扫描通过，其他则为失败")
    @GetMapping("/qualitygate")
    public TaskMeasureVO getQualityResult(@ApiParam(value = "projectKey")@RequestParam("projectKey") String projectKey){
        return MyBeanUtils.convertType(sonarRestService.getQualityGate(projectKey),TaskMeasureVO.class);
    }

    @ApiOperation(value = "获得扫描任务的所有缺陷")
    @GetMapping("/issues")
    public ScanIssuesVO getAllIssues(@ApiParam(value = "projectKey")@RequestParam("projectKey") String projectKey){
        return  MyBeanUtils.convertType( taskInfoService.getAllIssues(projectKey),ScanIssuesVO.class);
    }

    @ApiOperation(value = "获得扫描任务的总体信息")
    @GetMapping("/generalinfo")
    public TaskMeasuresVO getGeneralInfo(@ApiParam(value = "projectKey")@RequestParam("projectKey") String projectKey) {
        return MyBeanUtils.convertType(taskInfoService.getGeneralInfo(projectKey), TaskMeasuresVO.class);
    }

    @ApiOperation(value = "获得文件的源代码")
    @GetMapping("/sources")
    public SourcesVO getSources(@ApiParam(value = "文件路径")@RequestParam("key") String key){
        return MyBeanUtils.convertType(taskInfoService.getSources(key), SourcesVO.class);
    }
}
