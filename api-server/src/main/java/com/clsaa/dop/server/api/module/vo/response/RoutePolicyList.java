package com.clsaa.dop.server.api.module.vo.response;

import com.clsaa.dop.server.api.module.vo.response.policyDetail.routingPolicyDetail.RoutingPolicyDetail;
import io.swagger.annotations.ApiModel;

import java.util.LinkedList;
import java.util.List;

@ApiModel(description = "api列表")
public class RoutePolicyList {
    private int totalCount;
    private int current;
    private List<RoutingPolicyDetail> routePolicyList;

    public RoutePolicyList() {
    }

    public RoutePolicyList(int totalCount, int current) {
        this.totalCount = totalCount;
        this.current = current;
        routePolicyList = new LinkedList<>();
    }

    public void addRoutePolicy(RoutingPolicyDetail routingPolicyDetail){
        routePolicyList.add(routingPolicyDetail);
    }

    public int getTotalCount() {
        return totalCount;
    }

    public int getCurrent() {
        return current;
    }

    public List<RoutingPolicyDetail> getRoutePolicyList() {
        return routePolicyList;
    }
}
