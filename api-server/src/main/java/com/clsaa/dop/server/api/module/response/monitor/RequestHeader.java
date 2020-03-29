package com.clsaa.dop.server.api.module.response.monitor;

import io.swagger.annotations.ApiModel;

@ApiModel(description = "请求头")
public class RequestHeader {
    private String key;
    private String value;

    public RequestHeader() {
    }

    public RequestHeader(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
