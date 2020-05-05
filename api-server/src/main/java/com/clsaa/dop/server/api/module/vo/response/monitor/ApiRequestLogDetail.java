package com.clsaa.dop.server.api.module.vo.response.monitor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "请求日志")
public class ApiRequestLogDetail {
    @ApiModelProperty(value = "logId")
    private String logId;

    @ApiModelProperty(value = "requestMethod")
    private String requestMethod;

    @ApiModelProperty(value = "requestPath")
    private String requestPath;

    @ApiModelProperty(value = "requestSize")
    private int requestSize;

    @ApiModelProperty(value = "responseStatus")
    private int responseStatus;

    @ApiModelProperty(value = "proxyTimeout")
    private int proxyTimeout;

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

    public ApiRequestLogDetail() {
    }

    public ApiRequestLogDetail(String logId, String requestMethod, String requestPath, int requestSize, int responseStatus, int proxyTimeout, int responseTimeout, int responseSize, String time, String clientIP, String serviceId) {
        this.logId = logId;
        this.requestMethod = requestMethod;
        this.requestPath = requestPath;
        this.requestSize = requestSize;
        this.responseStatus = responseStatus;
        this.proxyTimeout = proxyTimeout;
        this.responseTimeout = responseTimeout;
        this.responseSize = responseSize;
        this.time = time;
        this.clientIP = clientIP;
        this.serviceId = serviceId;
    }

    public String getLogId() {
        return logId;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public String getRequestPath() {
        return requestPath;
    }

    public int getRequestSize() {
        return requestSize;
    }

    public int getResponseStatus() {
        return responseStatus;
    }

    public int getProxyTimeout() {
        return proxyTimeout;
    }

    public int getResponseTimeout() {
        return responseTimeout;
    }

    public int getResponseSize() {
        return responseSize;
    }

    public String getTime() {
        return time;
    }

    public String getClientIP() {
        return clientIP;
    }

    public String getServiceId() {
        return serviceId;
    }
}
