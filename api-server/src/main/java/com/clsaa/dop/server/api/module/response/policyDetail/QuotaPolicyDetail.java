package com.clsaa.dop.server.api.module.response.policyDetail;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "配额策略详情")
public class QuotaPolicyDetail {
    @ApiModelProperty(value = "policyId",example = "123",dataType = "string")
    private String policyId;

    @ApiModelProperty(value = "name",example = "testPolicy",dataType = "string")
    private String name;

    @ApiModelProperty(value = "quota",example = "1000",dataType = "int")
    private int quota;

    @ApiModelProperty(value = "resetTime",example = "3600",dataType = "int")
    private int resetTime;

    public QuotaPolicyDetail(String policyId, String name, int quota, int resetTime) {
        this.policyId = policyId;
        this.name = name;
        this.quota = quota;
        this.resetTime = resetTime;
    }

    public QuotaPolicyDetail() {
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
