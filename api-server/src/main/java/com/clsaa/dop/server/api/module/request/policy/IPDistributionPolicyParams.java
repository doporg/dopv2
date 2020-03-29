package com.clsaa.dop.server.api.module.request.policy;

import com.clsaa.dop.server.api.module.configuration.IPDistributionPolicyConfig;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;


@ApiModel(description = "IP分发策略参数")
public class IPDistributionPolicyParams extends RoutingPolicyParams{
    @ApiModelProperty(value = "configuration",dataType = "Configuration")
    private List<IPDistributionPolicyConfig> configurations;

    public IPDistributionPolicyParams() {
    }

    public List<IPDistributionPolicyConfig> getConfigurations() {
        return configurations;
    }
}

