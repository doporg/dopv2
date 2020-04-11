package com.clsaa.dop.server.link.service;

import com.clsaa.dop.server.link.config.BizCodes;
import com.clsaa.dop.server.link.feign.ZipkinQueryInterface;
import com.clsaa.dop.server.link.model.Span;
import com.clsaa.dop.server.link.model.vo.TraceVO;
import com.clsaa.rest.result.bizassert.BizAssert;
import com.clsaa.rest.result.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ZipkinQueryService {

    @Autowired
    private ZipkinQueryInterface zipkinQueryInterface;

    /**
     * find by traceID
     * @param traceId traceId
     * @return Trace
     */
    public Span[] getTraceById(String traceId) {
        Span[] res;
        try {
            res = zipkinQueryInterface.getTraceById(traceId);
        } catch (NotFoundException e) {
            System.out.println("traceId不存在");
//            BizAssert.justInvalidParam(BizCodes.NOT_FOUND_TRACE);
            return null;
        }
        return res;
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
        list.add(convertTraceToVo("42bab38648369882",getTraceById("42bab38648369882")));
        list.add(convertTraceToVo("da1536541887c58c",getTraceById("da1536541887c58c")));
        list.add(convertTraceToVo("31c63ba714b7f6ea",getTraceById("31c63ba714b7f6ea")));
        list.add(convertTraceToVo("171d5ee431a29779",getTraceById("171d5ee431a29779")));
        list.add(convertTraceToVo("1a2c1d3577fda929",getTraceById("1a2c1d3577fda929")));
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
