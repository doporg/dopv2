package com.clsaa.dop.server.api.module.vo.response.monitor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "API简要信息")
public class ApiSimpleInfo {
    @ApiModelProperty(value = "apiId",example = "123",dataType = "String")
    private String apiId;

    @ApiModelProperty(value = "apiPath",example = "/v2/test",dataType = "String")
    private String apiPath;

    @ApiModelProperty(value = "apiName",example = "new",dataType = "String")
    private String apiName;

    @ApiModelProperty(value = "frequency",example = "1233",dataType = "int")
    private int frequency;

    @ApiModelProperty(value = "responseTime",example = "1300",dataType = "int")
    private int responseTime;

    @ApiModelProperty(value = "failTimes",example = "120",dataType = "int")
    private int failTimes;

    public ApiSimpleInfo(String apiId, String apiPath,String apiName) {
        this.apiId = apiId;
        this.apiPath = apiPath;
        this.apiName = apiName;
        this.frequency = 0;
        this.failTimes = 0;
        this.responseTime = 0;
    }
}
