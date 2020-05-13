package com.clsaa.dop.server.link.controller;

import com.clsaa.dop.server.link.model.vo.SpanVO;
import com.clsaa.dop.server.link.model.vo.TraceVO;
import com.clsaa.dop.server.link.service.ZipkinQueryService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/v2/link/trace")
public class TraceController {

    @Autowired
    private ZipkinQueryService zipkinQueryService;

    @GetMapping(value = "/{traceId}")
    @ApiOperation(value = "根据Id查询链路信息")
    public List<SpanVO> getTraceById(
            @ApiParam(value = "链路Id", required = true)@PathVariable(value = "traceId")String traceId) {
        System.out.println("traceId:" + traceId);
        List<SpanVO> res = zipkinQueryService.getTraceById(traceId);
        System.out.println("res" + res);
        return res;
    }

    @GetMapping
    @ApiOperation(value = "查询链路列表")
    public List<TraceVO> getTraceList(
            @ApiParam(value = "服务名")
            @RequestParam(value = "serviceName", required = false)String serviceName,
            @ApiParam(value = "span名称")
            @RequestParam(value = "spanName", required = false)String spanName,
            @ApiParam(value = "annotation")
            @RequestParam(value = "annotation", required = false)String annotation,
            @ApiParam(value = "最短耗时(以微秒为单位)")
            @RequestParam(value = "minDuration", required = false)Long minDuration,
            @ApiParam(value = "最长耗时(以微秒为单位)")
            @RequestParam(value = "maxDuration", required = false)Long maxDuration,
            @ApiParam(value = "查询截止时间")
            @RequestParam(value = "endTs", required = false)String endTimestamp,
            @ApiParam(value = "查询回溯时间(以毫秒为单位)")
            @RequestParam(value = "lookback", defaultValue = "3600000")Long lookback,
            @ApiParam(value = "返回最多链路数", defaultValue = "10")
            @RequestParam(value = "limit", defaultValue = "10")Integer limit) {

        System.out.println("serviceName: " + serviceName);
        System.out.println("spanName: " + spanName);
        System.out.println("annotation: " + annotation);
        System.out.println("minDur: " + minDuration);
        System.out.println("maxDur: " + maxDuration);
        System.out.println("endTs: " + endTimestamp);
        System.out.println("lookback: " + lookback);
        System.out.println("limit: " + limit);
        return zipkinQueryService.getTraceList(serviceName, spanName, annotation, minDuration, maxDuration, endTimestamp, lookback, limit);
    }
}
