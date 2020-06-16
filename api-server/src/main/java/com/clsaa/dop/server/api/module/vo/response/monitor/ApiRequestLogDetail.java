package com.clsaa.dop.server.api.module.vo.response.monitor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel(description = "请求日志")
public class ApiRequestLogDetail {
    @ApiModelProperty(value = "logId")
    private String logId;

    @ApiModelProperty(value = "requestMethod")
    private String requestMethod;

    @ApiModelProperty(value = "path")
    private String path;

    @ApiModelProperty(value = "requestSize")
    private int requestSize;

    @ApiModelProperty(value = "responseStatus")
    private int statusCode;

    @ApiModelProperty(value = "proxyTimeout")
    private int timeConsuming;

    @ApiModelProperty(value = "responseTimeout")
    private int responseTimeout;

    @ApiModelProperty(value = "responseSize")
    private int responseSize;

    @ApiModelProperty(value = "time")
    private String time;

    @ApiModelProperty(value = "clientIP")
    private String clientIP;

    @ApiModelProperty(value = "serviceId")
    private String serviceId;


    public ApiRequestLogDetail(String logId, String requestMethod, String path, int requestSize, int statusCode, int timeConsuming, int responseTimeout, int responseSize, String time, String clientIP, String serviceId) {
        this.logId = logId;
        this.requestMethod = requestMethod;
        this.path = path;
        this.requestSize = requestSize;
        this.statusCode = statusCode;
        this.timeConsuming = timeConsuming;
        this.responseTimeout = responseTimeout;
        this.responseSize = responseSize;
        this.time = time;
        this.clientIP = clientIP;
        this.serviceId = serviceId;
    }
}
