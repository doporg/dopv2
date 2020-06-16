package com.clsaa.dop.server.api.controller;

import com.clsaa.dop.server.api.module.vo.request.policy.*;
import com.clsaa.dop.server.api.module.vo.response.CurrentLimitPolicyList;
import com.clsaa.dop.server.api.module.vo.response.ResponseResult;
import com.clsaa.dop.server.api.module.vo.response.RoutePolicyList;
import com.clsaa.dop.server.api.module.vo.response.policyDetail.CurrentLimitPolicyDetail;
import com.clsaa.dop.server.api.module.vo.response.policyDetail.routingPolicyDetail.RoutingPolicyDetail;
import com.clsaa.dop.server.api.service.PolicyService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/v2/api/policy")
@EnableAutoConfiguration
public class PolicyController {
    private PolicyService policyService;

    @Autowired
    public PolicyController(PolicyService policyService) {
        this.policyService = policyService;
    }

    @ApiOperation(value = "查看限流策略列表")
    @GetMapping("/flowControl/currentLimit")
    @ApiResponses({
            @ApiResponse(code = 400, message = "错误参数")
    })
    public ResponseResult<CurrentLimitPolicyList> getCurrentLimitPolicy(@ApiParam(name = "pageSize", required = true) @RequestParam("pageSize") int pageSize,
                                                                        @ApiParam(name = "pageNo", required = true) @RequestParam("pageNo") int pageNo) {
        return policyService.getCurrentLimitPolicies(pageNo, pageSize);
    }

    @ApiOperation(value = "查看限流策略详情")
    @GetMapping("/flowControl/currentLimit/{policyId}")
    @ApiResponses({
            @ApiResponse(code = 400, message = "错误参数")
    })
    public ResponseResult<CurrentLimitPolicyDetail> getCurrentLimitPolicyDetail(@ApiParam(name = "policy id", required = true) @PathVariable("policyId") String policyId) {
        return policyService.getCurrentLimitPolicyDetail(policyId);
    }

    @ApiOperation(value = "创建限流策略")
    @PostMapping("/flowControl/currentLimit")
    @ApiResponses({
            @ApiResponse(code = 400, message = "错误参数")
    })
    public ResponseResult<String> createCurrentLimitPolicy(@ApiParam(name = "limitPolicy params", required = true) @RequestBody CurrentLimitPolicyParam policyParams) {
        return policyService.createCurrentLimitPolicy(policyParams);
    }

    @ApiOperation(value = "修改限流策略")
    @PatchMapping("/flowControl/currentLimit/{policyId}")
    @ApiResponses({
            @ApiResponse(code = 400, message = "错误参数")
    })
    public ResponseResult modifyCurrentLimitPolicy(@ApiParam(name = "policy id", required = true) @PathVariable("policyId") String policyId,
                                                   @ApiParam(name = "limitPolicy params", required = true) @RequestBody CurrentLimitPolicyParam policyParams) {
        return policyService.modifyCurrentLimitPolicy(policyParams , policyId);
    }

    @ApiOperation(value = "删除限流策略")
    @DeleteMapping("/flowControl/currentLimit/{policyId}")
    @ApiResponses({
            @ApiResponse(code = 400, message = "错误参数")
    })
    public ResponseResult deleteCurrentLimitPolicy(@ApiParam(name = "policy id", required = true) @PathVariable("policyId") String policyId) {
        return policyService.deleteCurrentLimitPolicy(policyId);
    }

    @ApiOperation(value = "搜索限流策略")
    @GetMapping("/flowControl/currentLimit/search")
    @ApiResponses({
            @ApiResponse(code = 400, message = "错误参数")
    })
    public ResponseResult<List<CurrentLimitPolicyDetail>> searchCurrentLimitPolicy(@ApiParam(name = "policy name", required = true) @RequestParam("value") String value) {
        return policyService.searchCurrentLimitPolicy(value);
    }

    @ApiOperation(value = "查看路由策略")
    @GetMapping("/route")
    @ApiResponses({
            @ApiResponse(code = 400, message = "错误参数")
    })
    public ResponseResult<RoutePolicyList> getRoutingPolicy(@ApiParam(name = "policy type", required = true, allowableValues = "All,WeightingPolicy,ServiceDiscoveryPolicy") @RequestParam("type") String type,
                                                            @ApiParam(name = "pageNo", required = true) @RequestParam("pageNo") int pageNo,
                                                            @ApiParam(name = "pageSize", required = true) @RequestParam("pageSize") int pageSize) {
        return policyService.getRoutingPolicy(pageNo, pageSize, type);
    }

    @ApiOperation(value = "搜索路由策略")
    @GetMapping("/route/search")
    @ApiResponses({
            @ApiResponse(code = 400, message = "错误参数")
    })
    public ResponseResult<List<RoutingPolicyDetail>> searchRoutingPolicy(@ApiParam(name = "policy name", required = true) @RequestParam("value") String value) {
        return policyService.searchRoutingPolicy(value);
    }

    @ApiOperation(value = "查看路由策略详情")
    @GetMapping("/route/{policyId}")
    @ApiResponses({
            @ApiResponse(code = 400, message = "错误参数")
    })
    public ResponseResult<RoutingPolicyDetail> getRoutingPolicyDetail(@ApiParam(name = "policy id", required = true) @PathVariable("policyId") String policyId) {
        return policyService.getRoutingPolicyDetail(policyId);
    }

    @ApiOperation(value = "创建权重策略")
    @PostMapping("/route/weightingPolicy")
    @ApiResponses({
            @ApiResponse(code = 400, message = "错误参数")
    })
    public ResponseResult<String> createWeightingPolicyDetail(@ApiParam(name = "weightingPolicyDetail params", required = true) @RequestBody WeightingPolicyParams policyParams) {
        return policyService.createWeightingPolicy(policyParams.getName(), policyParams.getDescription(), policyParams.getAlgorithm(), policyParams.getHashOn(), policyParams.getHeader(), policyParams.getPath(), policyParams.getTargets());
    }

    @ApiOperation(value = "创建服务发现策略")
    @PostMapping("/route/serviceDiscoveryPolicy")
    @ApiResponses({
            @ApiResponse(code = 400, message = "错误参数")
    })
    public ResponseResult<String> createServiceDiscoveryPolicy(@ApiParam(name = "serviceDiscoveryPolicy params", required = true) @RequestBody ServiceDiscoveryPolicyParams policyParams) {
        return policyService.createServiceDiscoveryPolicy(policyParams.getName(), policyParams.getDescription(),
                policyParams.getTargetHost(), policyParams.getTargetPort(), policyParams.getTargetPath());
    }


    @ApiOperation(value = "删除路由策略")
    @DeleteMapping("/route/{policyId}")
    @ApiResponses({
            @ApiResponse(code = 400, message = "错误参数")
    })
    public ResponseResult deleteRoutingPolicy(@ApiParam(name = "policy id", required = true) @PathVariable("policyId") String policyId) {
        return policyService.deleteRoutingPolicy(policyId);
    }

    @ApiOperation(value = "修改权重策略")
    @PatchMapping("/route/weightingPolicy/{policyId}")
    @ApiResponses({
            @ApiResponse(code = 400, message = "错误参数")
    })
    public ResponseResult modifyWeightingPolicy(@ApiParam(name = "policy id", required = true) @PathVariable("policyId") String policyId,
                                                @ApiParam(name = "weightingPolicy params", required = true) @RequestBody WeightingPolicyParams policyParams) {
        return policyService.modifyWeightingPolicy(policyId, policyParams.getName(), policyParams.getDescription(), policyParams.getAlgorithm(), policyParams.getHashOn(), policyParams.getHeader(), policyParams.getPath(), policyParams.getTargets());
    }

    @ApiOperation(value = "修改服务发现策略")
    @PatchMapping("/route/serviceDiscoveryPolicy/{policyId}")
    @ApiResponses({
            @ApiResponse(code = 400, message = "错误参数")
    })
    public ResponseResult modifyServiceDiscoveryPolicy(@ApiParam(name = "policy id", required = true) @PathVariable("policyId") String policyId,
                                                       @ApiParam(name = "serviceDiscoveryPolicy params", required = true) @RequestBody ServiceDiscoveryPolicyParams policyParams) {
        return policyService.modifyServiceDiscoveryPolicy(policyId, policyParams.getName(), policyParams.getDescription(), policyParams.getTargetHost(), policyParams.getTargetPort(), policyParams.getTargetPath());
    }

}
