package com.clsaa.dop.server.api.module.response.policyDetail.routingPolicyDetail;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(description = "权重策略详情")
public class ServiceDiscoveryPolicyDetail extends RoutingPolicyDetail{
    @ApiModelProperty(value = "serviceName",dataType = "String")
    private String serviceName;

    @ApiModelProperty(value = "serviceName",example = "http://TESTWEB/test/add",dataType = "String")
    private String targetPath;

    @ApiModelProperty(value = "serviceName",dataType = "String",allowableValues = "RANDOM,POLLING,AVAILABLE.TIME")
    private String loadAlgorithm;

    public ServiceDiscoveryPolicyDetail() {
    }

    public ServiceDiscoveryPolicyDetail(String policyId, String name, String description, String serviceName, String targetPath, String loadAlgorithm) {
        super(policyId, name, description);
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

