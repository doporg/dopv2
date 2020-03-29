package com.clsaa.dop.server.api.module.request.policy;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "路由策略参数")
public class RoutingPolicyParams {

    @ApiModelProperty(value = "name",example = "testPolicy",dataType = "string")
    private String name;

    @ApiModelProperty(value = "description",example = "this is a policy",dataType = "string")
    private String description;

    public RoutingPolicyParams(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public RoutingPolicyParams() {
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
