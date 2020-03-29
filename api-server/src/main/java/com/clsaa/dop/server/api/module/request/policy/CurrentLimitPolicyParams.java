package com.clsaa.dop.server.api.module.request.policy;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "限流策略参数")
public class CurrentLimitPolicyParams {
    @ApiModelProperty(value = "name",example = "testPolicy",dataType = "string")
    private String name;

    @ApiModelProperty(value = "cycle",example = "10",dataType = "int")
    private int cycle;

    @ApiModelProperty(value = "requests",example = "60",dataType = "int")
    private int requests;

    public CurrentLimitPolicyParams(String name, int cycle, int requests) {
        this.name = name;
        this.cycle = cycle;
        this.requests = requests;
    }

    public CurrentLimitPolicyParams() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCycle() {
        return cycle;
    }

    public void setCycle(int cycle) {
        this.cycle = cycle;
    }

    public int getRequests() {
        return requests;
    }

    public void setRequests(int requests) {
        this.requests = requests;
    }
}
