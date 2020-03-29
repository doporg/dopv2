package com.clsaa.dop.server.api.module.response.monitor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "API数据统计")
public class TrafficStatistics {
    @ApiModelProperty(value = "throughput")
    private double throughput;

    @ApiModelProperty(value = "successfulRequests")
    private int successfulRequests;

    @ApiModelProperty(value = "failedRequests")
    private int failedRequests;

    @ApiModelProperty(value = "responseTime")
    private double responseTime;

    @ApiModelProperty(value = "requestData")
    private RequestData[] requestData;

    @ApiModelProperty(value = "highFrequencyApi")
    private ApiSimpleInfo[] highFrequencyApi;

    @ApiModelProperty(value = "timeConsumingApi")
    private ApiSimpleInfo[] timeConsumingApi;

    @ApiModelProperty(value = "callFailedApi")
    private ApiSimpleInfo[] callFailedApi;

    public TrafficStatistics() {
    }

    public TrafficStatistics(double throughput, int successfulRequests, int failedRequests, double responseTime,
                             RequestData[] requestData, ApiSimpleInfo[] highFrequencyApi, ApiSimpleInfo[] timeConsumingApi, ApiSimpleInfo[] callFailedApi) {
        this.throughput = throughput;
        this.successfulRequests = successfulRequests;
        this.failedRequests = failedRequests;
        this.responseTime = responseTime;
        this.requestData = requestData;
        this.highFrequencyApi = highFrequencyApi;
        this.timeConsumingApi = timeConsumingApi;
        this.callFailedApi = callFailedApi;
    }

    public double getThroughput() {
        return throughput;
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

    public RequestData[] getRequestData() {
        return requestData;
    }

    public ApiSimpleInfo[] getHighFrequencyApi() {
        return highFrequencyApi;
    }

    public ApiSimpleInfo[] getTimeConsumingApi() {
        return timeConsumingApi;
    }

    public ApiSimpleInfo[] getCallFailedApi() {
        return callFailedApi;
    }
}
