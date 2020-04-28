package com.clsaa.dop.server.api.module.vo.response.monitor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(description = "API数据统计")
public class TrafficStatistics {

    @ApiModelProperty(value = "successfulRequests")
    private int successfulRequests;

    @ApiModelProperty(value = "failedRequests")
    private int failedRequests;

    @ApiModelProperty(value = "responseTime")
    private double responseTime;

    @ApiModelProperty(value = "requestData")
    private RequestData[] requestData;

    @ApiModelProperty(value = "highFrequencyApi")
    private List<ApiSimpleInfo> highFrequencyApi;

    @ApiModelProperty(value = "timeConsumingApi")
    private List<ApiSimpleInfo> timeConsumingApi;

    @ApiModelProperty(value = "callFailedApi")
    private List<ApiSimpleInfo> callFailedApi;

    public TrafficStatistics() {
    }

    public TrafficStatistics(int successfulRequests, int failedRequests, double responseTime,
                             RequestData[] requestData, List<ApiSimpleInfo> highFrequencyApi, List<ApiSimpleInfo> timeConsumingApi,List<ApiSimpleInfo> callFailedApi) {
        this.successfulRequests = successfulRequests;
        this.failedRequests = failedRequests;
        this.responseTime = responseTime;
        this.requestData = requestData;
        this.highFrequencyApi = highFrequencyApi;
        this.timeConsumingApi = timeConsumingApi;
        this.callFailedApi = callFailedApi;
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


    public List<ApiSimpleInfo> getHighFrequencyApi() {
        return highFrequencyApi;
    }

    public List<ApiSimpleInfo> getTimeConsumingApi() {
        return timeConsumingApi;
    }

    public List<ApiSimpleInfo> getCallFailedApi() {
        return callFailedApi;
    }

    public void setSuccessfulRequests(int successfulRequests) {
        this.successfulRequests = successfulRequests;
    }

    public void setFailedRequests(int failedRequests) {
        this.failedRequests = failedRequests;
    }

    public void setResponseTime(double responseTime) {
        this.responseTime = responseTime;
    }

    public void setRequestData(RequestData[] requestData) {
        this.requestData = requestData;
    }

    public void setHighFrequencyApi(List<ApiSimpleInfo> highFrequencyApi) {
        this.highFrequencyApi = highFrequencyApi;
    }

    public void setTimeConsumingApi(List<ApiSimpleInfo> timeConsumingApi) {
        this.timeConsumingApi = timeConsumingApi;
    }

    public void setCallFailedApi(List<ApiSimpleInfo> callFailedApi) {
        this.callFailedApi = callFailedApi;
    }
}
