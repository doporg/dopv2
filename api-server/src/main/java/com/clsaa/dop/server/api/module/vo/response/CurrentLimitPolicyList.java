package com.clsaa.dop.server.api.module.vo.response;

import com.clsaa.dop.server.api.module.vo.response.policyDetail.CurrentLimitPolicyDetail;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@Data
@NoArgsConstructor
public class CurrentLimitPolicyList {
    private int totalCount;
    private int current;
    private List<CurrentLimitPolicyDetail> routePolicyList;

    public CurrentLimitPolicyList(int totalCount, int current) {
        this.totalCount = totalCount;
        this.current = current;
        routePolicyList = new LinkedList<>();
    }

    public void addCurrentLimitPolicy(CurrentLimitPolicyDetail currentLimitPolicyDetail){
        routePolicyList.add(currentLimitPolicyDetail);
    }
}
