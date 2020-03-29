package com.clsaa.dop.server.api.module.response.monitor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "请求日志")
public class RequestLog {
    @ApiModelProperty(value = "logId")
    private String logId;
    @ApiModelProperty(value = "path")
    private String path;
    @ApiModelProperty(value = "statusCode")
    private int statusCode;
    @ApiModelProperty(value = "timeConsuming")
    private int timeConsuming;
    @ApiModelProperty(value = "message")
    private String message;
    @ApiModelProperty(value = "time")
    private String time;

    public RequestLog() {
    }

    public RequestLog(String logId, String path, int statusCode, int timeConsuming, String message, String time) {
        this.logId = logId;
        this.path = path;
        this.statusCode = statusCode;
        this.timeConsuming = timeConsuming;
        this.message = message;
        this.time = time;
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

    public String getMessage() {
        return message;
    }

    public String getTime() {
        return time;
    }
}
