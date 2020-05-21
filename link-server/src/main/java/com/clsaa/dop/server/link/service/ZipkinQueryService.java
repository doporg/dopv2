package com.clsaa.dop.server.link.service;

import com.clsaa.dop.server.link.feign.ZipkinQueryInterface;
import com.clsaa.dop.server.link.model.vo.SpanVO;
import com.clsaa.dop.server.link.model.vo.TraceVO;
import com.clsaa.rest.result.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ZipkinQueryService {

    @Autowired
    private ZipkinQueryInterface zipkinQueryInterface;

    /**
     * find by traceID
     * @param traceId traceId
     * @return Trace
     */
    public List<SpanVO> getTraceById(String traceId) {
//        SpanVO[] res;
        List<SpanVO> res;
        try {
            res = zipkinQueryInterface.getTraceById(traceId);
        } catch (NotFoundException e) {
            System.out.println("traceId不存在");
//            BizAssert.justInvalidParam(BizCodes.NOT_FOUND_TRACE);
            return new ArrayList<>();
        }
        return res;
    }

    public List<TraceVO> getTraceList(String serviceName, String spanName, String annotation, Long minDuration,
                                      Long maxDuration, String endTimestamp, Long lookback, int limit) {

        List<List<SpanVO>> list =
                zipkinQueryInterface.getTraces(
                        buildName(serviceName),
                        buildName(spanName),
                        buildName(annotation),
                        buildDuration(minDuration),
                        buildDuration(maxDuration),
                        buildEndTs(endTimestamp),
                        lookback, limit);

        return list.stream().map(this::convertSpanListToTraceVO).collect(Collectors.toList());
    }

    private TraceVO convertSpanListToTraceVO(List<SpanVO> spanVOList) {
        TraceVO traceVO = new TraceVO();
        if (spanVOList.size() > 0) {
            String traceId = spanVOList.get(0).getTraceId();
            SpanVO rootSpan = new SpanVO();
            boolean hasError = false;
            Set<String> includeServices = new HashSet<>();
            for (SpanVO span: spanVOList) {
                if (span.getId().equalsIgnoreCase(traceId) && span.getParentId() == null) {
                    rootSpan = span;
                }
                if (!hasError && span.getTags() != null && span.getTags().containsKey("error")) {
                    hasError = true;
                }
                if (span.getLocalEndpoint() != null) {
                    includeServices.add(span.getLocalEndpoint().getServiceName());
                }
            }
            traceVO.setTraceId(traceId);
            traceVO.setServiceNum(includeServices.size());
            traceVO.setSpanNum(spanVOList.size());
            traceVO.setHasError(hasError);
            traceVO.setSpanName(rootSpan.getName());
            traceVO.setTimestamp(rootSpan.getTimestamp());
            traceVO.setDuration(rootSpan.getDuration());
        }
        return traceVO;
    }

    private String buildName(String name) {
        if (null == name || name.trim().length() == 0 || name.equalsIgnoreCase("all")) {
            return "";
        } else {
            return name;
        }
    }

    private Long buildDuration(Long duration) {
        return duration <= 0 ? null : duration;
    }

    private Long buildEndTs(String endTs) {
        return endTs == null ? new Date().getTime() : Long.parseLong(endTs);
    }
}
