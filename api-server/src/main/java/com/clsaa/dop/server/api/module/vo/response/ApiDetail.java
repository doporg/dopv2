package com.clsaa.dop.server.api.module.vo.response;

import com.clsaa.dop.server.api.module.vo.request.lifeCycle.FusePolicy;
import com.clsaa.dop.server.api.module.vo.response.policyDetail.CurrentLimitPolicyDetail;
import com.clsaa.dop.server.api.module.vo.response.policyDetail.routingPolicyDetail.RoutingPolicyDetail;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "api详情")
public class ApiDetail {
    @ApiModelProperty(value = "apiId",example = "123",dataType = "String")
    private String apiId;

    @ApiModelProperty(value = "name",example = "test api",dataType = "String")
    private String name;

    @ApiModelProperty(value = "description",example = "this is a api",dataType = "String")
    private String description;

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

    @ApiModelProperty(value = "routingPolicies",dataType = "RoutingPolicyDetail[]")
    private RoutingPolicyDetail[] routingPolicies;

    @ApiModelProperty(value = "currentLimitPolicies",dataType = "CurrentLimitPolicyDetail[]")
    private CurrentLimitPolicyDetail[] currentLimitPolicies;


    public ApiDetail() {
    }

    public ApiDetail(String apiId, String name, String description, boolean state, String requestMethod, String requestPath,
                     Long timeout, boolean caching, Long cachingTime, FusePolicy fusePolicy, RoutingPolicyDetail[] routingPolicies, CurrentLimitPolicyDetail[] currentLimitPolicies) {
        this.apiId = apiId;
        this.name = name;
        this.description = description;
        this.state = state;
        this.requestMethod = requestMethod;
        this.requestPath = requestPath;
        this.timeout = timeout;
        this.caching = caching;
        this.cachingTime = cachingTime;
        this.fusePolicy = fusePolicy;
        this.routingPolicies = routingPolicies;
        this.currentLimitPolicies = currentLimitPolicies;
    }

    public String getApiId() {
        return apiId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
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

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
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

    public RoutingPolicyDetail[] getRoutingPolicies() {
        return routingPolicies;
    }

    public void setRoutingPolicies(RoutingPolicyDetail[] routingPolicies) {
        this.routingPolicies = routingPolicies;
    }

    public CurrentLimitPolicyDetail[] getCurrentLimitPolicies() {
        return currentLimitPolicies;
    }

    public void setCurrentLimitPolicies(CurrentLimitPolicyDetail[] currentLimitPolicies) {
        this.currentLimitPolicies = currentLimitPolicies;
    }
}
