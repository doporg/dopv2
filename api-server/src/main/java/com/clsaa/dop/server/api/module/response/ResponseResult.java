package com.clsaa.dop.server.api.module.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "返回值")
public class ResponseResult<T> {
    @ApiModelProperty(value = "code",example = "0")
    private int code;
    @ApiModelProperty(value = "message",example = "this is a message.")
    private String message;
    @ApiModelProperty(value = "body")
    private T body;

    public ResponseResult(int code, String message, T body) {
        this.code = code;
        this.message = message;
        this.body = body;
    }

    public ResponseResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseResult() {
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getBody() {
        return body;
    }
}
