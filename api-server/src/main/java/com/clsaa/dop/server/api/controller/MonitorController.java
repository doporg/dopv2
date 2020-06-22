package com.clsaa.dop.server.api.controller;

import com.clsaa.dop.server.api.module.kong.logModule.KongHttpLog;
import com.clsaa.dop.server.api.module.vo.response.monitor.ApiRequestLogDetail;
import com.clsaa.dop.server.api.module.vo.response.monitor.ApiRequestLogList;
import com.clsaa.dop.server.api.module.vo.response.monitor.TrafficStatistics;
import com.clsaa.dop.server.api.module.vo.response.ResponseResult;
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
    public ResponseResult<TrafficStatistics> getTrafficStatistics(@ApiParam(name = "time")@RequestParam("time")int time){
        return monitorService.getTrafficStatistics(time);
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
    public ResponseResult<ApiRequestLogList> getRequestLog(@ApiParam(name = "begin time")@RequestParam("begin_time")String beginTime,
                                                             @ApiParam(name = "end time")@RequestParam("end_time")String endTime,
                                                             @ApiParam(name = "status code")@RequestParam("status_code")int statusCode,
                                                             @ApiParam(name = "pageNo", required = true) @RequestParam("pageNo") int pageNo,
                                                             @ApiParam(name = "pageSize", required = true) @RequestParam("pageSize") int pageSize){
        return monitorService.getRequestLog(beginTime,endTime,statusCode,pageNo,pageSize);
    }

    @ApiOperation(value = "查看API请求日志")
    @GetMapping("/log/list/{apiId}")
    @ApiResponses({
            @ApiResponse(code = 400,message = "错误参数")
    })
    public ResponseResult<ApiRequestLogList> getApiRequestLog(@ApiParam(name = "pageNo", required = true) @RequestParam("pageNo") int pageNo,
                                                              @ApiParam(name = "pageSize", required = true) @RequestParam("pageSize") int pageSize,
                                                              @ApiParam(name = "api Id", required = true)@PathVariable("apiId")String apiId){
        return monitorService.getRequestLog(apiId,pageNo,pageSize);
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
