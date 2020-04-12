package com.clsaa.dop.server.api.module.vo.request.policy;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "限流策略参数")
public class CurrentLimitPolicyParam {
    @ApiModelProperty(value = "name",example = "testPolicy",dataType = "string")
    private String name;

    @ApiModelProperty(value = "cycle",dataType = "String",allowableValues = "second,minute,hour,day")
    private String cycle;

    @ApiModelProperty(value = "requests",example = "60",dataType = "int")
    private int requests;

    @ApiModelProperty(value = "serviceId",dataType = "String")
    private String serviceId;

    public CurrentLimitPolicyParam(String name, String cycle, int requests, String serviceId) {
        this.name = name;
        this.cycle = cycle;
        this.requests = requests;
        this.serviceId = serviceId;
    }

    public CurrentLimitPolicyParam() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
    }

    public int getRequests() {
        return requests;
    }

    public void setRequests(int requests) {
        this.requests = requests;
    }

    public String getServiceId() {
        return serviceId;
    }
}
