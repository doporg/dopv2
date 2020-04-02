package com.clsaa.dop.server.link.service;

import com.clsaa.dop.server.link.model.Span;
import com.clsaa.dop.server.link.model.vo.TraceVO;
import com.clsaa.dop.server.link.util.NotFoundTraceException;
import com.clsaa.dop.server.link.util.RestTemplateErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Service
public class ZipkinQueryService {

    private static final String bathUrl = "http://link-server/api/v2";

    @Autowired
    private RestTemplate restTemplate;

    /**
     * find by traceID
     * @param traceId traceId
     * @return Trace
     */
    public TraceVO getTraceById(String traceId) {
        restTemplate.setErrorHandler(new RestTemplateErrorHandler());
        Span[] spanList;

        try{
            spanList = restTemplate.getForObject(bathUrl + "/trace/" + traceId, Span[].class);
        } catch (NotFoundTraceException e) {
            System.out.println("traceId不存在");
            return null;
        }
        System.out.println("------------------------------------------------------------");
        for (Span span : spanList) {
            System.out.println(span.toString());
        }
        return this.convertTraceToVo(traceId, spanList);
    }

    public List<TraceVO> getTraceList(String serviceName, String spanName, String annotation, String minDuration,
                                      String maxDuration, String endTimestamp, long lookback, int limit) {
        System.out.println("serviceName: " + serviceName);
        System.out.println("spanName: " + spanName);
        System.out.println("annotation: " + annotation);
        System.out.println("minDuration: " + minDuration);
        System.out.println("maxDuration: " + maxDuration);
        System.out.println("endTs: " + endTimestamp);
        System.out.println("lookback: " + lookback);
        System.out.println("limit: " + limit);
        List<TraceVO> list = new ArrayList<>();
        list.add(getTraceById("42bab38648369882"));
        list.add(getTraceById("da1536541887c58c"));
        return list;
    }

    private TraceVO convertTraceToVo(String traceId, Span[] spanList) {
//        55555具体解析稍后再好好儿写
        if (null == spanList || spanList.length == 0) {
            return null;
        }
        TraceVO traceVO = new TraceVO();
        traceVO.setTraceId(traceId);
        traceVO.setSpanNum(spanList.length);
        boolean hasError = Arrays.stream(spanList).anyMatch(
                span -> span.getTags().containsKey("error"));
        Span[] rootSpan = Arrays.stream(spanList).filter(
                span -> (span.getId().equals(traceId) && span.getParentId() == null)).toArray(Span[]::new);
        if (rootSpan.length == 0) {
            System.out.println("没找到父节点，非常不应该");
        }
        int num = 1 + spanList.length/2; // 暂时先这么写，之后再研究
        traceVO.setDuration(rootSpan[0].getDuration());
        traceVO.setSpanName(rootSpan[0].getName());
        traceVO.setTimestamp(rootSpan[0].getTimestamp());
        traceVO.setHasError(hasError);
        traceVO.setDepth(num);
        traceVO.setServiceNum(num);
        traceVO.setSpansList(spanList);
        return traceVO;
    }
}
