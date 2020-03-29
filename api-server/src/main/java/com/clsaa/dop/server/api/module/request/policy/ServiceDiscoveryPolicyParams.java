package com.clsaa.dop.server.api.module.request.policy;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(description = "权重策略详情")
public class ServiceDiscoveryPolicyParams extends RoutingPolicyParams{
    @ApiModelProperty(value = "serviceName",dataType = "String")
    private String serviceName;

    @ApiModelProperty(value = "serviceName",example = "http://TESTWEB/test/add",dataType = "String")
    private String targetPath;

    @ApiModelProperty(value = "serviceName",dataType = "String",allowableValues = "RANDOM,POLLING,AVAILABLE.TIME")
    private String loadAlgorithm;

    public ServiceDiscoveryPolicyParams() {
    }

    public ServiceDiscoveryPolicyParams(String name, String description, String serviceName, String targetPath, String loadAlgorithm) {
        super( name, description);
        this.serviceName = serviceName;
        this.targetPath = targetPath;
        this.loadAlgorithm = loadAlgorithm;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getTargetPath() {
        return targetPath;
    }

    public String getLoadAlgorithm() {
        return loadAlgorithm;
    }
}

