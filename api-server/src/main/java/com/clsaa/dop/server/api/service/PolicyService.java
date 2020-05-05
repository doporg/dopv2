package com.clsaa.dop.server.api.service;

import com.clsaa.dop.server.api.module.configuration.WeightingPolicyConfig;
import com.clsaa.dop.server.api.module.po.CurrentLimitPolicy;
import com.clsaa.dop.server.api.module.po.Service;
import com.clsaa.dop.server.api.module.vo.request.policy.CurrentLimitPolicyParam;
import com.clsaa.dop.server.api.module.vo.response.CurrentLimitPolicyList;
import com.clsaa.dop.server.api.module.vo.response.ResponseResult;
import com.clsaa.dop.server.api.module.vo.response.RoutePolicyList;
import com.clsaa.dop.server.api.module.vo.response.policyDetail.CurrentLimitPolicyDetail;
import com.clsaa.dop.server.api.module.vo.response.policyDetail.routingPolicyDetail.RoutingPolicyDetail;

import java.util.List;

public interface PolicyService {

    ResponseResult<String> createServiceDiscoveryPolicy(String name,String description,String host,Long port,String path);

    ResponseResult<String> createWeightingPolicy(String name, String description, String algorithm, String hashOn, String header, String path, List<WeightingPolicyConfig> configs);

    ResponseResult deleteRoutingPolicy(String policyId);

    ResponseResult modifyServiceDiscoveryPolicy(String policyId,String name,String description,String host,Long port,String path);

    ResponseResult modifyWeightingPolicy(String policyId,String name, String description, String algorithm, String hashOn, String header, String path, List<WeightingPolicyConfig> configs);

    ResponseResult<RoutingPolicyDetail> getRoutingPolicyDetail(String policyId);

    ResponseResult<RoutePolicyList> getRoutingPolicy(int pageNo, int pageSize, String type);

    ResponseResult<List<RoutingPolicyDetail>> searchRoutingPolicy(String value);

    ResponseResult<String> createCurrentLimitPolicy(CurrentLimitPolicyParam policyParams);

    ResponseResult modifyCurrentLimitPolicy(CurrentLimitPolicyParam policyParams, String policyId);

    ResponseResult deleteCurrentLimitPolicy(String policyId);

    ResponseResult<CurrentLimitPolicyList> getCurrentLimitPolicies(int pageNo, int pageSize);

    ResponseResult<CurrentLimitPolicyDetail> getCurrentLimitPolicyDetail(String policyId);

    ResponseResult<List<CurrentLimitPolicyDetail>> searchCurrentLimitPolicy(String value);

    boolean updateServiceCurrentLimitPolicy(Service service, CurrentLimitPolicy currentLimitPolicy);
}


