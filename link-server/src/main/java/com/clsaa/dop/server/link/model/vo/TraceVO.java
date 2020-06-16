package com.clsaa.dop.server.link.model.vo;

import com.clsaa.dop.server.link.model.po.Span;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TraceVO {

    /*traceid
	timestamp(server start timestamp)*/
    private String traceId;

    private String spanName;

    private int spanNum;

    private int serviceNum;

    private int depth;

    private long timestamp; // server receive time, timestamp of root span

    private long duration;

    private boolean hasError; //出现错误了吗

//    private SpanVO[] spansList;
}
