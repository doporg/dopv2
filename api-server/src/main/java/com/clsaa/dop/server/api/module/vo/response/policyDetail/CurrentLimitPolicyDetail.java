package com.clsaa.dop.server.api.module.vo.response.policyDetail;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "限流策略详情")
public class CurrentLimitPolicyDetail {
    @ApiModelProperty(value = "policyId",example = "123",dataType = "string")
    private String policyId;

    @ApiModelProperty(value = "name",example = "testPolicy",dataType = "string")
    private String name;

    @ApiModelProperty(value = "minute",example = "60",dataType = "int")
    private int second;
    @ApiModelProperty(value = "minute",example = "60",dataType = "int")
    private int minute;
    @ApiModelProperty(value = "minute",example = "60",dataType = "int")
    private int hour;

    @ApiModelProperty(value = "minute",example = "60",dataType = "int")
    private int day;
}
