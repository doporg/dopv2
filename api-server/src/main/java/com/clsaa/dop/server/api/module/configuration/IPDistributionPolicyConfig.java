package com.clsaa.dop.server.api.module.configuration;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "IP分发策略配置")
public class IPDistributionPolicyConfig {
    @ApiModelProperty(value = "ip",example = "196.216.74.109",dataType = "String")
    private String ip;

    @ApiModelProperty(value = "targetPath",example = "http://196.216.74.109",dataType = "String")
    private String targetPath;

    public IPDistributionPolicyConfig() {
    }

    public IPDistributionPolicyConfig(String ip, String targetPath) {
        this.ip = ip;
        this.targetPath = targetPath;
    }

    public String getIp() {
        return ip;
    }

    public String getTargetPath() {
        return targetPath;
    }
}
