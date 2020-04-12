package com.clsaa.dop.server.api.module.vo.request.policy;

import com.clsaa.dop.server.api.module.configuration.WeightingPolicyConfig;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;


@ApiModel(description = "权重策略详情")
public class WeightingPolicyParams extends RoutingPolicyParams{
    @ApiModelProperty(value = "configuration",dataType = "Configuration")
    private List<WeightingPolicyConfig> configurations;

    @ApiModelProperty(value = "algorithm",dataType = "String",allowableValues = "round-robin,hash,least-connections")
    private String algorithm;

    @ApiModelProperty(value = "hashOn",dataType = "String",allowableValues = "header,ip")
    private String hashOn;

    @ApiModelProperty(value = "header",dataType = "String",example = "host")
    private String header;

    @ApiModelProperty(value = "path",dataType = "String",example = "/test")
    private String path;

    public WeightingPolicyParams() {
    }

    public List<WeightingPolicyConfig> getConfigurations() {
        return configurations;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public String getHashOn() {
        return hashOn;
    }

    public String getHeader() {
        return header;
    }

    public String getPath() {
        return path;
    }
}

