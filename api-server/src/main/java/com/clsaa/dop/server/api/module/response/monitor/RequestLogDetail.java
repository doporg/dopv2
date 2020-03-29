package com.clsaa.dop.server.api.module.response.monitor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(description = "请求日志")
public class RequestLogDetail {
    @ApiModelProperty(value = "logId")
    private String logId;

    @ApiModelProperty(value = "path")
    private String path;

    @ApiModelProperty(value = "statusCode")
    private int statusCode;

    @ApiModelProperty(value = "timeConsuming")
    private int timeConsuming;

    @ApiModelProperty(value = "requestTime")
    private String requestTime;

    @ApiModelProperty(value = "responseTime")
    private String responseTime;

    @ApiModelProperty(value = "headers")
    private List<RequestHeader> headers;

    public RequestLogDetail() {
    }

    public RequestLogDetail(String logId, String path, int statusCode, int timeConsuming, String requestTime,
                            String responseTime, List<RequestHeader> headers) {
        this.logId = logId;
        this.path = path;
        this.statusCode = statusCode;
        this.timeConsuming = timeConsuming;
        this.requestTime = requestTime;
        this.responseTime = responseTime;
        this.headers = headers;
    }

    public String getLogId() {
        return logId;
    }

    public String getPath() {
        return path;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public int getTimeConsuming() {
        return timeConsuming;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public String getResponseTime() {
        return responseTime;
    }

    public List<RequestHeader> getHeaders() {
        return headers;
    }
}
