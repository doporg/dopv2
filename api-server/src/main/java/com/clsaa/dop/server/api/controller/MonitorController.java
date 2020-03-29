package com.clsaa.dop.server.api.controller;

import com.clsaa.dop.server.api.module.response.monitor.RequestLog;
import com.clsaa.dop.server.api.module.response.monitor.RequestLogDetail;
import com.clsaa.dop.server.api.module.response.monitor.TrafficStatistics;
import com.clsaa.dop.server.api.module.response.ResponseResult;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/v2/api/monitor")
@EnableAutoConfiguration
public class MonitorController {

    @ApiOperation(value = "查看流量统计")
    @GetMapping("/statistics")
    @ApiResponses({
            @ApiResponse(code = 400,message = "错误参数")
    })
    public ResponseResult<TrafficStatistics> getTrafficStatistics(@ApiParam(name = "frequency", required = true,allowableValues = "3,24")@RequestParam("frequency")int frequency){
        return new ResponseResult<>(0,"success");
    }

    @ApiOperation(value = "查看请求日志")
    @GetMapping("/log")
    @ApiResponses({
            @ApiResponse(code = 400,message = "错误参数")
    })
    public ResponseResult<List<RequestLog>> getRequestLog(@ApiParam(name = "begin time")@RequestParam("beginTime")String beginTime,
                                                          @ApiParam(name = "end time")@RequestParam("endTime")String endTime,
                                                          @ApiParam(name = "status code")@RequestParam("statusCode")int statusCode){
        return new ResponseResult<>(0,"success");
    }

    @ApiOperation(value = "查看请求日志详情")
    @GetMapping("/log/{logId}")
    @ApiResponses({
            @ApiResponse(code = 400,message = "错误参数")
    })
    public ResponseResult<RequestLogDetail> getRequestLogDetail(@ApiParam(name = "log Id", required = true)@PathVariable("logId")String logId){
        return new ResponseResult<>(0,"success");
    }
}
