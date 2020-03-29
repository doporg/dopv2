package com.clsaa.dop.server.api.module.request.policy;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "配额策略参数")
public class QuotaPolicyParams {
    @ApiModelProperty(value = "name",example = "testPolicy",dataType = "string")
    private String name;

    @ApiModelProperty(value = "quota",example = "1000",dataType = "int")
    private int quota;

    @ApiModelProperty(value = "resetTime",example = "3600",dataType = "int")
    private int resetTime;


    public QuotaPolicyParams(String name, int quota, int resetTime) {
        this.name = name;
        this.quota = quota;
        this.resetTime = resetTime;
    }

    public QuotaPolicyParams() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuota() {
        return quota;
    }

    public void setQuota(int quota) {
        this.quota = quota;
    }

    public int getResetTime() {
        return resetTime;
    }

    public void setResetTime(int resetTime) {
        this.resetTime = resetTime;
    }
}
