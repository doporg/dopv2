package com.clsaa.dop.server.api.module.vo.response.monitor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "API数据统计")
public class TrafficStatistics {

    @ApiModelProperty(value = "successfulRequests")
    private int successfulRequests;

    @ApiModelProperty(value = "failedRequests")
    private int failedRequests;

    @ApiModelProperty(value = "responseTime")
    private double responseTime;

    @ApiModelProperty(value = "throughput")
    private int throughput;

    @ApiModelProperty(value = "errorRate")
    private double errorRate;

    @ApiModelProperty(value = "highFrequencyApi")
    private List<ApiSimpleInfo> highFrequencyApi;

    @ApiModelProperty(value = "timeConsumingApi")
    private List<ApiSimpleInfo> timeConsumingApi;

    @ApiModelProperty(value = "callFailedApi")
    private List<ApiSimpleInfo> callFailedApi;
}
