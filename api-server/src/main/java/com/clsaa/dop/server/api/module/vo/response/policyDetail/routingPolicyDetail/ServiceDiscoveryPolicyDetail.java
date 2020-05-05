package com.clsaa.dop.server.api.module.vo.response.policyDetail.routingPolicyDetail;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(description = "权重策略详情")
public class ServiceDiscoveryPolicyDetail extends RoutingPolicyDetail{


    @ApiModelProperty(value = "targetHost",example = "baidu.com",dataType = "String")
    private String targetHost;

    @ApiModelProperty(value = "targetPort",example = "80",dataType = "Long")
    private Long targetPort;

    @ApiModelProperty(value = "targetPath",example = "/test/add",dataType = "String")
    private String targetPath;

    public ServiceDiscoveryPolicyDetail() {
    }

    public ServiceDiscoveryPolicyDetail(String policyId, String name, String description, String targetHost, Long targetPort, String targetPath) {
        super(policyId, name, description,"ServiceDiscoveryPolicy");
        this.targetHost = targetHost;
        this.targetPort = targetPort;
        this.targetPath = targetPath;
    }

    public String getTargetPath() {
        return targetPath;
    }

    public void setTargetPath(String targetPath) {
        this.targetPath = targetPath;
    }

    public String getTargetHost() {
        return targetHost;
    }

    public void setTargetHost(String targetHost) {
        this.targetHost = targetHost;
    }

    public Long getTargetPort() {
        return targetPort;
    }

    public void setTargetPort(Long targetPort) {
        this.targetPort = targetPort;
    }
}

