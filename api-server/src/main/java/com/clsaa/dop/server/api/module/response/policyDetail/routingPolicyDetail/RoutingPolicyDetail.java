package com.clsaa.dop.server.api.module.response.policyDetail.routingPolicyDetail;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "路由策略详情")
public class RoutingPolicyDetail {
    @ApiModelProperty(value = "policyId",example = "123",dataType = "string")
    private String policyId;

    @ApiModelProperty(value = "name",example = "testPolicy",dataType = "string")
    private String name;

    @ApiModelProperty(value = "description",example = "this is a policy",dataType = "string")
    private String description;

    public RoutingPolicyDetail(String policyId, String name, String description) {
        this.policyId = policyId;
        this.name = name;
        this.description = description;
    }

    public RoutingPolicyDetail() {
    }

    public String getPolicyId() {
        return policyId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
