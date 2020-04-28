package com.clsaa.dop.server.api.module.configuration;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "权重策略配置")
public class WeightingPolicyConfig {
    @ApiModelProperty(value = "targetPath",example = "196.216.74.109",dataType = "String")
    private String targetHost;

    @ApiModelProperty(value = "targetPort",example = "80",dataType = "long")
    private int targetPort;

    @ApiModelProperty(value = "weights",example = "100",dataType = "int")
    private int weights;


    public WeightingPolicyConfig(String targetHost, int targetPort, int weights) {
        this.targetHost = targetHost;
        this.targetPort = targetPort;
        this.weights = weights;
    }

    public WeightingPolicyConfig() {
    }

    public String getTargetHost() {
        return targetHost;
    }

    public int getTargetPort() {
        return targetPort;
    }

    public int getWeights() {
        return weights;
    }
}
