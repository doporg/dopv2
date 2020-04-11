package com.clsaa.dop.server.link.feign;

import com.clsaa.dop.server.link.model.Span;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("link-server")
public interface ZipkinQueryInterface {

    @GetMapping("/api/v2/trace/{traceId}")
    Span[] getTraceById(@PathVariable("traceId") String traceId);

}
