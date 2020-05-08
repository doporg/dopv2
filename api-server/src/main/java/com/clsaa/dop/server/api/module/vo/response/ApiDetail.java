package com.clsaa.dop.server.api.module.vo.response;

import com.clsaa.dop.server.api.module.vo.request.lifeCycle.FusePolicy;
import com.clsaa.dop.server.api.module.vo.response.policyDetail.CurrentLimitPolicyDetail;
import com.clsaa.dop.server.api.module.vo.response.policyDetail.routingPolicyDetail.RoutingPolicyDetail;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "api详情")
public class ApiDetail {
    @ApiModelProperty(value = "apiId",example = "123",dataType = "String")
    private String apiId;

    @ApiModelProperty(value = "name",example = "test api",dataType = "String")
    private String name;

    @ApiModelProperty(value = "description",example = "this is a api",dataType = "String")
    private String description;

    @ApiModelProperty(value = "health",example = "HEALTHY",dataType = "String")
    private String health;

    @ApiModelProperty(value = "state",example = "false",dataType = "boolean")
    private boolean state;

    @ApiModelProperty(value = "requestMethod",example = "GET",dataType = "String",allowableValues="GET, POST, PUT, PATCH, DELETE, COPY, HEAD, OPTIONS, LINK, UNLINK, PURGE, LOCK, UNLOCK, PROPFIND, VIEW")
    private String requestMethod;

    @ApiModelProperty(value = "requestPath",example = "/test",dataType = "String")
    private String requestPath;

    @ApiModelProperty(value = "timeout",dataType = "int",example = "100")
    private Long timeout;

    @ApiModelProperty(value = "caching",dataType = "boolean",example = "true")
    private boolean caching;

    @ApiModelProperty(value = "cachingTime",dataType = "int",example = "1000")
    private Long cachingTime;

    @ApiModelProperty(value = "fusePolicy",dataType = "FusePolicy")
    private FusePolicy fusePolicy;

    @ApiModelProperty(value = "routingPolicy",dataType = "RoutingPolicyDetail")
    private RoutingPolicyDetail routingPolicy;

    @ApiModelProperty(value = "limitPolicy",dataType = "CurrentLimitPolicyDetail")
    private CurrentLimitPolicyDetail currentLimitPolicy;
}
