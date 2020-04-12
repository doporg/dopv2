package com.clsaa.dop.server.api.module.vo.request.lifeCycle;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "创建api参数类")
public class CreateApiParams {
    @ApiModelProperty(value = "name",example = "test-api",dataType = "String",notes = "only can contains '-' and '_'" )
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
    private String[] routingPolicyId;

    public CreateApiParams() {
    }

    public CreateApiParams(String name, String description, String state, String requestMethod, String requestPath,
                           Long timeout, boolean caching, Long cachingTime, FusePolicy fusePolicy, String[] routingPolicyId) {
        this.name = name;
        this.description = description;
        this.requestMethod = requestMethod;
        this.requestPath = requestPath;
        this.timeout = timeout;
        this.caching = caching;
        this.cachingTime = cachingTime;
        this.fusePolicy = fusePolicy;
        this.routingPolicyId = routingPolicyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getRequestPath() {
        return requestPath;
    }

    public void setRequestPath(String requestPath) {
        this.requestPath = requestPath;
    }

    public Long getTimeout() {
        return timeout;
    }

    public void setTimeout(Long timeout) {
        this.timeout = timeout;
    }

    public boolean isCaching() {
        return caching;
    }

    public void setCaching(boolean caching) {
        this.caching = caching;
    }

    public Long getCachingTime() {
        return cachingTime;
    }

    public void setCachingTime(Long cachingTime) {
        this.cachingTime = cachingTime;
    }

    public FusePolicy getFusePolicy() {
        return fusePolicy;
    }

    public void setFusePolicy(FusePolicy fusePolicy) {
        this.fusePolicy = fusePolicy;
    }

    public String[] getRoutingPolicyId() {
        return routingPolicyId;
    }

    public void setRoutingPolicyId(String[] routingPolicyId) {
        this.routingPolicyId = routingPolicyId;
    }
}
