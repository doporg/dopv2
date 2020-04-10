package com.clsaa.dop.server.api.controller;

import com.clsaa.dop.server.api.module.kong.logModule.KongHttpLog;
import com.clsaa.dop.server.api.module.response.monitor.ApiRequestLog;
import com.clsaa.dop.server.api.module.response.monitor.ApiRequestLogDetail;
import com.clsaa.dop.server.api.module.response.monitor.TrafficStatistics;
import com.clsaa.dop.server.api.module.response.ResponseResult;
import com.clsaa.dop.server.api.service.MonitorService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/v2/api/monitor")
@EnableAutoConfiguration
public class MonitorController {
    private MonitorService monitorService;

    @Autowired
    public MonitorController(MonitorService monitorService) {
        this.monitorService = monitorService;
    }

    @ApiOperation(value = "查看流量统计")
    @GetMapping("/statistics")
    @ApiResponses({
            @ApiResponse(code = 400,message = "错误参数")
    })
    public ResponseResult<TrafficStatistics> getTrafficStatistics(@ApiParam(name = "frequency", required = true,allowableValues = "3,24")@RequestParam("frequency")int frequency){
        return new ResponseResult<>(0,"success");
    }

    @ApiOperation(value = "接收请求日志")
    @PostMapping("/log")
    @ApiResponses({
            @ApiResponse(code = 400,message = "错误参数")
    })
    public ResponseResult receiveRequestLog(@RequestBody KongHttpLog kongHttpLog){
        return monitorService.receiveRequestLog(kongHttpLog);
    }

    @ApiOperation(value = "查看请求日志")
    @GetMapping("/log")
    @ApiResponses({
            @ApiResponse(code = 400,message = "错误参数")
    })
    public ResponseResult<List<ApiRequestLog>> getRequestLog(@ApiParam(name = "begin time")@RequestParam("beginTime")String beginTime,
                                                             @ApiParam(name = "end time")@RequestParam("endTime")String endTime,
                                                             @ApiParam(name = "status code")@RequestParam("statusCode")int statusCode){
        return monitorService.getRequestLog(beginTime,endTime,statusCode);
    }

    @ApiOperation(value = "查看请求日志")
    @GetMapping("/logAll")
    @ApiResponses({
            @ApiResponse(code = 400,message = "错误参数")
    })
    public ResponseResult<List<ApiRequestLog>> getRequestLog(){
        return monitorService.getRequestLog();
    }

    @ApiOperation(value = "查看请求日志详情")
    @GetMapping("/log/{logId}")
    @ApiResponses({
            @ApiResponse(code = 400,message = "错误参数")
    })
    public ResponseResult<ApiRequestLogDetail> getRequestLogDetail(@ApiParam(name = "log Id", required = true)@PathVariable("logId")String logId){
        return monitorService.getRequestLogDetail(logId);
    }
}
