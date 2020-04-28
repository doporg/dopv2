package com.clsaa.dop.server.link.service;

import com.clsaa.dop.server.link.model.po.Span;
import org.springframework.stereotype.Service;

/**
 * 对本地trace的操作，自己的表
 */
@Service
public class TraceService {

    public void saveTrace(String traceId) {

    }

    public void deleteTrace(String traceId) {

    }

    public Span[] getTrace(String traceId) {
        return new Span[1];
    }

}
