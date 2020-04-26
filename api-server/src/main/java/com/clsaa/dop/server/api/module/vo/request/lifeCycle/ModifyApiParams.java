package com.clsaa.dop.server.api.module.vo.request.lifeCycle;

import com.clsaa.dop.server.api.module.vo.request.policy.CurrentLimitPolicyParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "创建api参数类")
public class ModifyApiParams {

    @ApiModelProperty(value = "name",example = "test api",dataType = "String")
    private String name;

    @ApiModelProperty(value = "description",example = "this is a api",dataType = "String")
    private String description;

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

    @ApiModelProperty(value = "routingPolicyId",dataType = "String[]")
    private String routingPolicyId;

    @ApiModelProperty(value = "currentLimitPolicyId",dataType = "String")
    private String currentLimitPolicyId;
}
