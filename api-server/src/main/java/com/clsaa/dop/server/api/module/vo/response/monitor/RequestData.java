package com.clsaa.dop.server.api.module.vo.response.monitor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "请求数据")
public class RequestData {
    @ApiModelProperty(value = "time")
    private String time;

    @ApiModelProperty(value = "successfulRequests")
    private int successfulRequests;

    @ApiModelProperty(value = "failedRequests")
    private int failedRequests;

    @ApiModelProperty(value = "responseTime")
    private double responseTime;

    public RequestData() {
    }

    public RequestData(String time, int successfulRequests, int failedRequests, double responseTime) {
        this.time = time;
        this.successfulRequests = successfulRequests;
        this.failedRequests = failedRequests;
        this.responseTime = responseTime;
    }

    public String getTime() {
        return time;
    }

    public int getSuccessfulRequests() {
        return successfulRequests;
    }

    public int getFailedRequests() {
        return failedRequests;
    }

    public double getResponseTime() {
        return responseTime;
    }
}
