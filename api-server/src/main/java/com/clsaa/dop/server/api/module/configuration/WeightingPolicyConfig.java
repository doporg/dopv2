package com.clsaa.dop.server.api.module.configuration;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "权重策略配置")
public class WeightingPolicyConfig {
    @ApiModelProperty(value = "targetPath",example = "http://196.216.74.109",dataType = "String")
    private String targetPath;

    @ApiModelProperty(value = "weights",example = "80",dataType = "int")
    private int weights;

    public WeightingPolicyConfig(String targetPath, int weights) {
        this.targetPath = targetPath;
        this.weights = weights;
    }

    public WeightingPolicyConfig() {
    }

    public String getTargetPath() {
        return targetPath;
    }

    public int getWeights() {
        return weights;
    }
}
