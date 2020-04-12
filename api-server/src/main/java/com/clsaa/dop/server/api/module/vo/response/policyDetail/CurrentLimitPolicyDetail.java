package com.clsaa.dop.server.api.module.vo.response.policyDetail;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "限流策略详情")
public class CurrentLimitPolicyDetail {
    @ApiModelProperty(value = "policyId",example = "123",dataType = "string")
    private String policyId;

    @ApiModelProperty(value = "name",example = "testPolicy",dataType = "string")
    private String name;

    @ApiModelProperty(value = "cycle",allowableValues = "second,minute,hour,day",dataType = "String")
    private String cycle;

    @ApiModelProperty(value = "requests",example = "60",dataType = "int")
    private int requests;

    public CurrentLimitPolicyDetail(String policyId, String name, String cycle, int requests) {
        this.policyId = policyId;
        this.name = name;
        this.cycle = cycle;
        this.requests = requests;
    }

    public CurrentLimitPolicyDetail() {
    }

    public String getPolicyId() {
        return policyId;
    }

    public void setPolicyId(String policyId) {
        this.policyId = policyId;
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
}
