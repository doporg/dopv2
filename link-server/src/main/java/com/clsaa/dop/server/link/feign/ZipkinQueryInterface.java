package com.clsaa.dop.server.link.feign;

import com.clsaa.dop.server.link.model.vo.SpanVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("link-server")
public interface ZipkinQueryInterface {

    @GetMapping("/api/v2/trace/{traceId}")
    List<SpanVO> getTraceById(@PathVariable("traceId") String traceId);

    @GetMapping("/api/v2/traces")
    List<List<SpanVO>> getTraces(@RequestParam("serviceName") String serviceName,
                         @RequestParam("spanName") String spanName,
                         @RequestParam("annotationQuery") String annotationQuery,
                         @RequestParam("minDuration") Long minDuration,
                         @RequestParam("maxDuration") Long maxDuration,
                         @RequestParam("endTs") Long endTs,
                         @RequestParam("lookback") Long lookback,
                         @RequestParam("limit") int limit);

}
