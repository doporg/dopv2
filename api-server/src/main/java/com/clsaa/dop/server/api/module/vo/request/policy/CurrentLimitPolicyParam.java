package com.clsaa.dop.server.api.module.vo.request.policy;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "限流策略参数")
public class CurrentLimitPolicyParam {
    @ApiModelProperty(value = "name",example = "testPolicy",dataType = "string")
    private String name;

    @ApiModelProperty(value = "description",example = "testPolicy",dataType = "string")
    private String description;

    @ApiModelProperty(value = "minute",example = "60",dataType = "int")
    private int second;

    @ApiModelProperty(value = "minute",example = "60",dataType = "int")
    private int minute;

    @ApiModelProperty(value = "minute",example = "60",dataType = "int")
    private int hour;

    @ApiModelProperty(value = "minute",example = "60",dataType = "int")
    private int day;
}
