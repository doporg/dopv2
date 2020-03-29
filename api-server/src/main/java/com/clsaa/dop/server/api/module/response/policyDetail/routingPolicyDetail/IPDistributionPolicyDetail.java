package com.clsaa.dop.server.api.module.response.policyDetail.routingPolicyDetail;

import com.clsaa.dop.server.api.module.configuration.IPDistributionPolicyConfig;
import com.clsaa.dop.server.api.module.request.policy.RoutingPolicyParams;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;


@ApiModel(description = "IP分发策略详情")
public class IPDistributionPolicyDetail extends RoutingPolicyDetail{
    @ApiModelProperty(value = "configuration",dataType = "Configuration")
    private List<IPDistributionPolicyConfig> configurations;

    public IPDistributionPolicyDetail() {
    }

    public List<IPDistributionPolicyConfig> getConfigurations() {
        return configurations;
    }
}

