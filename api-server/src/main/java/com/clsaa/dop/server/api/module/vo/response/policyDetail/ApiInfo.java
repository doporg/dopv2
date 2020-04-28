package com.clsaa.dop.server.api.module.vo.response.policyDetail;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "api信息")
public class ApiInfo {
    @ApiModelProperty(value = "id",example = "123",dataType = "String")
    private String id;

    @ApiModelProperty(value = "name",example = "test api",dataType = "String")
    private String name;

    @ApiModelProperty(value = "path",example = "/test",dataType = "String")
    private String path;

    @ApiModelProperty(value = "description",example = "this is a api",dataType = "String")
    private String description;

    private String createUserName;

    @ApiModelProperty(value = "health",example = "HEALTHY",dataType = "String")
    private String health;

    @ApiModelProperty(value = "state",example = "false",dataType = "boolean")
    private boolean state;

    public ApiInfo() {
    }

    public ApiInfo(String id, String name, String description, String path, String createUserName, String health, boolean state) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.path = path;
        this.createUserName = createUserName;
        this.health = health;
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPath() {
        return path;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public String getHealth() {
        return health;
    }

    public boolean isState() {
        return state;
    }
}
