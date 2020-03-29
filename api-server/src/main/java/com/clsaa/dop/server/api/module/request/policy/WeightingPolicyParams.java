package com.clsaa.dop.server.api.module.request.policy;

import com.clsaa.dop.server.api.module.configuration.WeightingPolicyConfig;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;


@ApiModel(description = "权重策略详情")
public class WeightingPolicyParams extends RoutingPolicyParams{
    @ApiModelProperty(value = "configuration",dataType = "Configuration")
    private List<WeightingPolicyConfig> configurations;

    public WeightingPolicyParams() {
    }

    public List<WeightingPolicyConfig> getConfigurations() {
        return configurations;
    }
}

