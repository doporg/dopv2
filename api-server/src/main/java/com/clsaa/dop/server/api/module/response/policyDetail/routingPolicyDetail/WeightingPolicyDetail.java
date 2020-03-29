package com.clsaa.dop.server.api.module.response.policyDetail.routingPolicyDetail;

import com.clsaa.dop.server.api.module.configuration.WeightingPolicyConfig;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

@ApiModel(description = "权重策略详情")
public class WeightingPolicyDetail extends RoutingPolicyDetail{
    @ApiModelProperty(value = "configuration",dataType = "Configuration")
    private List<WeightingPolicyConfig> configurations;

    public WeightingPolicyDetail() {
    }

    public WeightingPolicyDetail(String policyId, String name, String description) {
        super(policyId, name, description);
        this.configurations = new ArrayList<>();
    }

    public List<WeightingPolicyConfig> getConfigurations() {
        return configurations;
    }

    public void addConfiguration(String targetPath, int weights){
        WeightingPolicyConfig configuration = new WeightingPolicyConfig(targetPath,weights);
        configurations.add(configuration);
    }
}

