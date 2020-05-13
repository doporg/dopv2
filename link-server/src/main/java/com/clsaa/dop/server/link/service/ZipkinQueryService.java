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
////        for (List<SpanVO> spanList : list) {
////            System.out.println(spanList.toString());
////        }
////        list.add(convertTraceToVo("42bab38648369882",getTraceById("42bab38648369882")));
////        list.add(convertTraceToVo("da1536541887c58c",getTraceById("da1536541887c58c")));
////        list.add(convertTraceToVo("31c63ba714b7f6ea",getTraceById("31c63ba714b7f6ea")));
////        list.add(convertTraceToVo("171d5ee431a29779",getTraceById("171d5ee431a29779")));
////        list.add(convertTraceToVo("1a2c1d3577fda929",getTraceById("1a2c1d3577fda929")));
//        return res;
    }

    private TraceVO convertSpanListToTraceVO(List<SpanVO> spanVOList) {
        System.out.println("----------------\n"+spanVOList.toString());
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
//            traceVO.setDepth();
        }
        return traceVO;
    }

    private TraceVO convertTraceToVo(String traceId, List<SpanVO> spanList) {
//        55555具体解析稍后再好好儿写
        if (null == spanList || spanList.size() == 0) {
            return null;
        }
        TraceVO traceVO = new TraceVO();
        traceVO.setTraceId(traceId);
        traceVO.setSpanNum(spanList.size());
        boolean hasError = spanList.stream().anyMatch(
                span -> span.getTags().containsKey("error"));
        SpanVO[] rootSpan = spanList.stream().filter(
                span -> (span.getId().equals(traceId) && span.getParentId() == null)).toArray(SpanVO[]::new);
        if (rootSpan.length == 0) {
            System.out.println("没找到父节点，非常不应该");
        }
        int num = 1 + spanList.size()/2; // 暂时先这么写，之后再研究
        traceVO.setDuration(rootSpan[0].getDuration());
        traceVO.setSpanName(rootSpan[0].getName());
        traceVO.setTimestamp(rootSpan[0].getTimestamp());
        traceVO.setHasError(hasError);
//        traceVO.setDepth(num);
        traceVO.setServiceNum(num);
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
