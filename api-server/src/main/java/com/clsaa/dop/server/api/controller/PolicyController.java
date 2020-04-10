package com.clsaa.dop.server.api.controller;

import com.clsaa.dop.server.api.module.request.policy.*;
import com.clsaa.dop.server.api.module.response.ResponseResult;
import com.clsaa.dop.server.api.module.response.policyDetail.CurrentLimitPolicyDetail;
import com.clsaa.dop.server.api.module.response.policyDetail.routingPolicyDetail.RoutingPolicyDetail;
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

    @ApiOperation(value = "查看限流策略")
    @GetMapping("/flowControl/currentLimit/getByService/{serviceId}")
    @ApiResponses({
            @ApiResponse(code = 400,message = "错误参数")
    })
    public ResponseResult<List<CurrentLimitPolicyDetail>> getCurrentLimitPolicy(@ApiParam(name = "service id", required = true) @PathVariable("serviceId") String serviceId){
        return policyService.getCurrentLimitPolicies(serviceId);
    }

    @ApiOperation(value = "创建限流策略")
    @PostMapping("/flowControl/currentLimit/create")
    @ApiResponses({
            @ApiResponse(code = 400,message = "错误参数")
    })
    public ResponseResult<String> createCurrentLimitPolicy(@ApiParam(name = "currentLimitPolicy params", required = true) @RequestBody CurrentLimitPolicyParam policyParams){
        return policyService.createCurrentLimitPolicy(policyParams.getName(),policyParams.getCycle(),policyParams.getRequests(),policyParams.getServiceId());
    }

    @ApiOperation(value = "修改限流策略")
    @PatchMapping("/flowControl/currentLimit/modify/{policyId}")
    @ApiResponses({
            @ApiResponse(code = 400,message = "错误参数")
    })
    public ResponseResult modifyCurrentLimitPolicy(@ApiParam(name = "policy id", required = true) @PathVariable("policyId") String policyId,
                                                   @ApiParam(name = "currentLimitPolicy params", required = true) @RequestBody CurrentLimitPolicyParam policyParams){
        return policyService.modifyCurrentLimitPolicy(policyParams.getName(),policyParams.getCycle(),policyParams.getRequests(),policyId);
    }

    @ApiOperation(value = "删除限流策略")
    @DeleteMapping("/flowControl/currentLimit/delete/{policyId}")
    @ApiResponses({
            @ApiResponse(code = 400,message = "错误参数")
    })
    public ResponseResult deleteCurrentLimitPolicy(@ApiParam(name = "policy id", required = true) @PathVariable("policyId") String policyId){
        return policyService.deleteCurrentLimitPolicy(policyId);
    }

    @ApiOperation(value = "查看路由策略")
    @GetMapping("/route/get")
    @ApiResponses({
            @ApiResponse(code = 400,message = "错误参数")
    })
    public ResponseResult<List<RoutingPolicyDetail>> getRoutingPolicy(@ApiParam(name = "policy type", required = true,allowableValues = "All,WeightingPolicy,ServiceDiscoveryPolicy")@RequestParam("type")String type){
        return policyService.getRoutingPolicy(type);
    }

    @ApiOperation(value = "查看路由策略详情")
    @GetMapping("/route/get/{policyId}")
    @ApiResponses({
            @ApiResponse(code = 400,message = "错误参数")
    })
    public ResponseResult<RoutingPolicyDetail> getRoutingPolicyDetail(@ApiParam(name = "policy id", required = true) @PathVariable("policyId") String policyId){
        return policyService.getRoutingPolicyDetail(policyId);
    }

    @ApiOperation(value = "创建权重策略")
    @PostMapping("/route/addWeightingPolicy")
    @ApiResponses({
            @ApiResponse(code = 400,message = "错误参数")
    })
    public ResponseResult<String> createWeightingPolicyDetail(@ApiParam(name = "weightingPolicyDetail params", required = true) @RequestBody WeightingPolicyParams policyParams){
        return policyService.createWeightingPolicy(policyParams.getName(),policyParams.getDescription(),policyParams.getAlgorithm(),policyParams.getHashOn(),policyParams.getHeader(),policyParams.getPath(),policyParams.getConfigurations());
    }

    @ApiOperation(value = "创建服务发现策略")
    @PostMapping("/route/addServiceDiscoveryPolicy")
    @ApiResponses({
            @ApiResponse(code = 400,message = "错误参数")
    })
    public ResponseResult<String> createServiceDiscoveryPolicy(@ApiParam(name = "serviceDiscoveryPolicy params", required = true) @RequestBody ServiceDiscoveryPolicyParams policyParams){
        return policyService.createServiceDiscoveryPolicy(policyParams.getName(),policyParams.getDescription(),
                policyParams.getTargetHost(),policyParams.getTargetPort(),policyParams.getTargetPath());
    }


    @ApiOperation(value = "删除路由策略")
    @DeleteMapping("/route/delete/{policyId}")
    @ApiResponses({
            @ApiResponse(code = 400,message = "错误参数")
    })
    public ResponseResult deleteRoutingPolicy(@ApiParam(name = "policy id", required = true) @PathVariable("policyId") String policyId){
        return policyService.deleteRoutingPolicy(policyId);
    }

    @ApiOperation(value = "修改权重策略")
    @PatchMapping("/route/modifyWeightingPolicy/{policyId}")
    @ApiResponses({
            @ApiResponse(code = 400,message = "错误参数")
    })
    public ResponseResult modifyWeightingPolicy(@ApiParam(name = "policy id", required = true) @PathVariable("policyId") String policyId,
                                                @ApiParam(name = "weightingPolicy params", required = true) @RequestBody WeightingPolicyParams policyParams){
        return policyService.modifyWeightingPolicy(policyId,policyParams.getName(),policyParams.getDescription(),policyParams.getAlgorithm(),policyParams.getHashOn(),policyParams.getHeader(),policyParams.getPath(),policyParams.getConfigurations());
    }

    @ApiOperation(value = "修改服务发现策略")
    @PatchMapping("/route/modifyServiceDiscoveryPolicy/{policyId}")
    @ApiResponses({
            @ApiResponse(code = 400,message = "错误参数")
    })
    public ResponseResult modifyServiceDiscoveryPolicy(@ApiParam(name = "policy id", required = true) @PathVariable("policyId") String policyId,
                                                @ApiParam(name = "serviceDiscoveryPolicy params", required = true) @RequestBody ServiceDiscoveryPolicyParams policyParams){
        return policyService.modifyServiceDiscoveryPolicy(policyId,policyParams.getName(),policyParams.getDescription(),policyParams.getTargetHost(),policyParams.getTargetPort(),policyParams.getTargetPath());
    }

}
