package com.clsaa.dop.server.api.module.response.monitor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "请求日志")
public class ApiRequestLog {
    @ApiModelProperty(value = "logId")
    private String logId;
    @ApiModelProperty(value = "path")
    private String path;
    @ApiModelProperty(value = "statusCode")
    private int statusCode;
    @ApiModelProperty(value = "timeConsuming")
    private int timeConsuming;
    @ApiModelProperty(value = "time")
    private String time;

    public ApiRequestLog() {
    }

    public ApiRequestLog(String logId, String path, int statusCode, int timeConsuming, String time) {
        this.logId = logId;
        this.path = path;
        this.statusCode = statusCode;
        this.timeConsuming = timeConsuming;
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

    public String getTime() {
        return time;
    }
}
