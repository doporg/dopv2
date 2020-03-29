package com.clsaa.dop.server.api.module.response.policyDetail.routingPolicyDetail;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(description = "头信息策略详情")
public class HeaderPolicyDetail extends RoutingPolicyDetail{
    @ApiModelProperty(value = "header",example = "head",dataType = "String")
    private String header;

    @ApiModelProperty(value = "header value",example = "123",dataType = "String")
    private String headerValue;

    @ApiModelProperty(value = "serviceName",example = "http://196.0.0.1/test/add",dataType = "String")
    private String targetPath;

    public HeaderPolicyDetail() {
    }

    public HeaderPolicyDetail(String policyId, String name, String description, String header, String headerValue, String targetPath) {
        super(policyId, name, description);
        this.header = header;
        this.headerValue = headerValue;
        this.targetPath = targetPath;
    }

    public HeaderPolicyDetail(String header, String headerValue, String targetPath) {
        this.header = header;
        this.headerValue = headerValue;
        this.targetPath = targetPath;
    }

    public String getHeader() {
        return header;
    }

    public String getHeaderValue() {
        return headerValue;
    }

    public String getTargetPath() {
        return targetPath;
    }
}

