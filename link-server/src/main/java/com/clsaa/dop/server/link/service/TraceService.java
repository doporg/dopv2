package com.clsaa.dop.server.link.service;

import com.clsaa.dop.server.link.feign.ZipkinQueryInterface;
import com.clsaa.dop.server.link.model.po.Span;
import com.clsaa.dop.server.link.model.vo.SpanVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 对本地trace的操作，自己的表
 */
@Service
public class TraceService {

    @Autowired
    private ZipkinQueryInterface zipkinQueryInterface;

    private static final long DefaultLookback = 1800000;
    private static final int DefaultLimit = 100;

    public double calculateRate(Long projectId, String serviceName, Date endTs) {

        List<List<SpanVO>> list =
                zipkinQueryInterface.getTraces(serviceName,"","",
                        null,null, endTs.getTime(),
                        DefaultLookback, DefaultLimit);
        int total = list.size();
        int errorCount = 0;
        for (List<SpanVO> trace : list) {
            boolean hasError = trace.stream().anyMatch(
                    spanVO -> spanVO.getLocalEndpoint() != null
                            && spanVO.getLocalEndpoint().getServiceName().equals(serviceName)
                            && spanVO.getTags().containsKey("error"));
            if (hasError) {
                errorCount++;
            }
        }
        return total == 0 ? 0 : errorCount * 1.0/total;
    }



    public void saveTrace(String traceId) {

    }

    public void deleteTrace(String traceId) {

    }

    public Span[] getTrace(String traceId) {
        return new Span[1];
    }

}
