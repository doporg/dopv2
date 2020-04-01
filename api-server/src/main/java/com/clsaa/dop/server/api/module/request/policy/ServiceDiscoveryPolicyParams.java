package com.clsaa.dop.server.api.module.request.policy;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(description = "服务发现策略详情")
public class ServiceDiscoveryPolicyParams extends RoutingPolicyParams{

    @ApiModelProperty(value = "targetPath",example = "/test/add",dataType = "String")
    private String targetPath;

    @ApiModelProperty(value = "targetHost",example = "baidu.com",dataType = "String")
    private String targetHost;

    @ApiModelProperty(value = "targetPort",example = "80",dataType = "Long")
    private Long targetPort;

    public ServiceDiscoveryPolicyParams() {
    }

    public ServiceDiscoveryPolicyParams(String name, String description, String targetPath, String targetHost, Long targetPort) {
        super(name, description);
        this.targetPath = targetPath;
        this.targetHost = targetHost;
        this.targetPort = targetPort;
    }

    public String getTargetPath() {
        return targetPath;
    }

    public String getTargetHost() {
        return targetHost;
    }

    public Long getTargetPort() {
        return targetPort;
    }
}

