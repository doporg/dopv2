package com.clsaa.dop.server.link.controller;

import com.clsaa.dop.server.link.model.Span;
import com.clsaa.dop.server.link.model.vo.TraceVO;
import com.clsaa.dop.server.link.service.ZipkinQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class TraceController {

    @Autowired
    private ZipkinQueryService zipkinQueryService;

    @GetMapping(value = "/getTraceById")
    public Span[] getTraceById(@RequestParam(value = "traceId")String traceId) {
        System.out.println("traceId:" + traceId);
        return zipkinQueryService.getTraceById(traceId);
    }

    @GetMapping(value = "/getTraceList")
    public List<TraceVO> getTraceList(
            @RequestParam(value = "serviceName", required = false)String serviceName,
            @RequestParam(value = "spanName", required = false)String spanName,
            @RequestParam(value = "annotation", required = false)String annotation,
            @RequestParam(value = "minDuration", required = false)String minDuration,
            @RequestParam(value = "maxDuration", required = false)String maxDuration,
            @RequestParam(value = "endTs", required = false)String endTimestamp,
            @RequestParam(value = "lookback", defaultValue = "3600000")int lookback,
            @RequestParam(value = "limit", defaultValue = "10")int limit) {

        return zipkinQueryService.getTraceList(serviceName, spanName, annotation, minDuration, maxDuration, endTimestamp, lookback, limit);
    }
}
