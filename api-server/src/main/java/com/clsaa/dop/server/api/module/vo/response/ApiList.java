package com.clsaa.dop.server.api.module.vo.response;

import com.clsaa.dop.server.api.module.vo.response.policyDetail.ApiInfo;
import io.swagger.annotations.ApiModel;

import java.util.LinkedList;
import java.util.List;

@ApiModel(description = "api列表")
public class ApiList {
    private int totalCount;
    private int current;
    private List<ApiInfo> apiList;

    public ApiList() {
    }

    public ApiList(int totalCount, int current) {
        this.totalCount = totalCount;
        this.current = current;
        apiList = new LinkedList<>();
    }

    public void addApiInfo(ApiInfo apiInfo){
        apiList.add(apiInfo);
    }

    public int getTotalCount() {
        return totalCount;
    }

    public int getCurrent() {
        return current;
    }

    public List<ApiInfo> getApiList() {
        return apiList;
    }
}
