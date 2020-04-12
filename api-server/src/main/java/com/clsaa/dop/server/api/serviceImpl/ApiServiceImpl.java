package com.clsaa.dop.server.api.serviceImpl;

import com.clsaa.dop.server.api.dao.RouteRepository;
import com.clsaa.dop.server.api.dao.ServiceRepository;
import com.clsaa.dop.server.api.dao.ServiceRouteRepository;
import com.clsaa.dop.server.api.module.po.*;
import com.clsaa.dop.server.api.module.kong.routeModule.KongRoute;
import com.clsaa.dop.server.api.module.kong.serviceModule.KongService;
import com.clsaa.dop.server.api.module.kong.upstreamModule.KongUpstream;
import com.clsaa.dop.server.api.module.vo.request.lifeCycle.CreateApiParams;
import com.clsaa.dop.server.api.module.vo.request.lifeCycle.FusePolicy;
import com.clsaa.dop.server.api.module.vo.request.lifeCycle.ModifyApiParams;
import com.clsaa.dop.server.api.module.vo.response.ApiDetail;
import com.clsaa.dop.server.api.module.vo.response.ResponseResult;
import com.clsaa.dop.server.api.module.vo.response.policyDetail.routingPolicyDetail.RoutingPolicyDetail;
import com.clsaa.dop.server.api.restTemplate.ApiRestTemplate;
import com.clsaa.dop.server.api.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@org.springframework.stereotype.Service
public class ApiServiceImpl implements ApiService {
    private ServiceRepository serviceRepository;
    private RouteRepository routeRepository;
    private ServiceRouteRepository serviceRouteRepository;
    private ApiRestTemplate apiRestTemplate;

    @Autowired
    public ApiServiceImpl(ServiceRepository serviceRepository, RouteRepository routeRepository, ServiceRouteRepository serviceRouteRepository,
                          ApiRestTemplate apiRestTemplate) {
        this.serviceRepository = serviceRepository;
        this.routeRepository = routeRepository;
        this.serviceRouteRepository = serviceRouteRepository;
        this.apiRestTemplate = apiRestTemplate;
    }

    @Override
    public ResponseResult<String> createApi(CreateApiParams createApiParams) {

        //查看是否有同名service
        if (serviceRepository.findByName(createApiParams.getName())==null){
            String upstreamId = createApiParams.getRoutingPolicyId()[0];
            ServiceRoute serviceRoute = serviceRouteRepository.findServiceRouteById(upstreamId);
            FusePolicy fusePolicy = createApiParams.getFusePolicy();
            //查看负载均衡策略是否存在
            if(serviceRoute !=null){
                KongService kongService = apiRestTemplate.createService(serviceRoute.getHost(),createApiParams.getName(),createApiParams.getTimeout(),"http", serviceRoute.getPort(),serviceRoute.getPath());
                KongUpstream kongUpstream = apiRestTemplate.modifyFusePolicy(serviceRoute.getHost(),fusePolicy);
                if (kongService!=null&&kongUpstream!=null){
                    //更新数据库
                    Service service = new Service(kongService.getId(),createApiParams.getName(),createApiParams.getDescription(),
                            createApiParams.getTimeout(),createApiParams.isCaching(),createApiParams.getCachingTime(),fusePolicy.isEnable(),
                            fusePolicy.getFuseDetectionRing(),fusePolicy.getCriticalFusingFailureRate(),fusePolicy.getFuseDuration(),
                            fusePolicy.getReplyDetectionRingSize(), serviceRoute);
                    serviceRepository.saveAndFlush(service);
                    Route route = new Route(false,createApiParams.getRequestMethod(),createApiParams.getRequestPath(),service);
                    routeRepository.saveAndFlush(route);

                    return new ResponseResult<>(0,"success",service.getId());
                }else {
                    return new ResponseResult<>(3,"fail");
                }
            }else {
                return new ResponseResult<>(2,"no route policy");
            }
        }else{
            return new ResponseResult<>(1,"name repeat");
        }
    }

    @Override
    public ResponseResult onlineApi(String apiId) {
        Route route = routeRepository.findRouteByServiceId(apiId);
        //查看route是否存在
        if (route!=null){
            if(!route.isOnline()){
                KongRoute kongRoute = apiRestTemplate.createRoute(route.getRequestMethod(),route.getRequestPath(),apiId);
                if (kongRoute!=null){
                    route.setKongRouteId(kongRoute.getId());
                    route.setOnline(true);
                    routeRepository.saveAndFlush(route);
                    return new ResponseResult(0,"success");
                }else {
                    return new ResponseResult(3,"kong error");
                }
            }else {
                return new ResponseResult(2,"already online");
            }
        }else {
            return new ResponseResult(1,"not Found");
        }
    }

    @Override
    public ResponseResult offlineApi(String apiId) {
        Route route = routeRepository.findRouteByServiceId(apiId);
        if (route!=null){
            if (route.isOnline()){
                if (apiRestTemplate.deleteRoute(route.getKongRouteId())){
                    route.setKongRouteId("");
                    route.setOnline(false);
                    routeRepository.saveAndFlush(route);
                    return new ResponseResult(0,"success");
                }else {
                    return new ResponseResult(2,"delete fail");
                }
            }else {
                return new ResponseResult(2,"already offline");
            }
        }else {
            return new ResponseResult(1,"not found");
        }
    }

    @Override
    public ResponseResult deleteApi(String apiId) {
        Route route = routeRepository.findRouteByServiceId(apiId);
        if (route!=null){
            if (!route.isOnline()){
                if (apiRestTemplate.deleteService(apiId)){
                    routeRepository.delete(route);
                    serviceRepository.deleteById(apiId);
                    return new ResponseResult(0,"success");
                }else{
                    return new ResponseResult(3,"kong error");
                }
            }else {
                return new ResponseResult(2,"service is online");
            }
        }else {
            return new ResponseResult(1,"not found");
        }
    }

    @Override
    public ResponseResult modifyApi(ModifyApiParams modifyApiParams) {
        //查看service是否存在
        String apiId = modifyApiParams.getApiId();
        Route route = routeRepository.findRouteByServiceId(apiId);
        if (serviceRepository.findServiceById(apiId)!=null&&route!=null){
            String upstreamId = modifyApiParams.getRoutingPolicyId()[0];
            ServiceRoute serviceRoute = serviceRouteRepository.findServiceRouteById(upstreamId);
            FusePolicy fusePolicy = modifyApiParams.getFusePolicy();

            //查看路由策略、限流策略是否存在
            if(serviceRoute !=null){
                //更新kong
                KongService kongService = apiRestTemplate.modifyService(apiId, serviceRoute.getHost(),modifyApiParams.getName(),modifyApiParams.getTimeout(),"http", 80L,serviceRoute.getPath());
                KongUpstream kongUpstream = apiRestTemplate.modifyFusePolicy(serviceRoute.getHost(),fusePolicy);
                if (kongService!=null&&kongUpstream!=null){
                    //更新数据库
                    Service service = new Service(apiId,modifyApiParams.getName(),modifyApiParams.getDescription(),
                            modifyApiParams.getTimeout(),modifyApiParams.isCaching(),modifyApiParams.getCachingTime(),fusePolicy.isEnable(),
                            fusePolicy.getFuseDetectionRing(),fusePolicy.getCriticalFusingFailureRate(),fusePolicy.getFuseDuration(),
                            fusePolicy.getReplyDetectionRingSize(), serviceRoute);
                    serviceRepository.saveAndFlush(service);

                    route.setRequestMethod(modifyApiParams.getRequestMethod());
                    route.setRequestPath(modifyApiParams.getRequestPath());
                    routeRepository.saveAndFlush(route);

                    if (route.isOnline()){
                        if (apiRestTemplate.modifyRoute(route.getKongRouteId(),route.getRequestMethod(),route.getRequestPath(),apiId)!=null){
                            return new ResponseResult<>(0,"success");
                        }else {
                            return new ResponseResult<>(4,"kong error");
                        }
                    }else {
                        return new ResponseResult<>(0,"success");
                    }
                }else {
                    return new ResponseResult<>(3,"fail");
                }
            }else {
                return new ResponseResult<>(2,"no route policy");
            }
        }else{
            return new ResponseResult<>(1,"not found");
        }
    }

    @Override
    public ResponseResult<ApiDetail> getApi(String apiId) {
        Route route = routeRepository.findRouteByServiceId(apiId);
        if(route!=null){
            Service service = route.getService();
            ServiceRoute serviceRoute = service.getServiceRoute();
            FusePolicy fusePolicy = new FusePolicy(service.isFuse(),service.getFuseDetectionRing(),service.getCriticalFusingFailureRate(),
                    service.getFuseDuration(),service.getReplyDetectionRingSize());
            RoutingPolicyDetail[] routingPolicies = new RoutingPolicyDetail[1];
            routingPolicies[0] = new RoutingPolicyDetail(serviceRoute.getId(),serviceRoute.getName(),serviceRoute.getDescription(),serviceRoute.getType());
            return new ResponseResult<>(0,"success",new ApiDetail(apiId,service.getName(),service.getDescription(),
                    route.isOnline(),route.getRequestMethod(),route.getRequestPath(),service.getTimeout(),service.isCaching(),
                    service.getCachingTime(),fusePolicy,routingPolicies,null));
        }else{
            return new ResponseResult<>(1,"no service");
        }
    }

    @Override
    public ResponseResult<ApiDetail[]> getApiList() {
        List<Route> routes = routeRepository.findAll();
        ApiDetail[] apiDetailList = new ApiDetail[routes.size()];
        for(int i = 0;i < routes.size();i++){
            Route route = routes.get(i);
            Service service = route.getService();
            FusePolicy fusePolicy = new FusePolicy(service.isFuse(),service.getFuseDetectionRing(),service.getCriticalFusingFailureRate(),
                    service.getFuseDuration(),service.getReplyDetectionRingSize());
            apiDetailList[i] = new ApiDetail(service.getId(),service.getName(),service.getDescription(),
                    route.isOnline(),route.getRequestMethod(),route.getRequestPath(),service.getTimeout(),service.isCaching(),
                    service.getCachingTime(),fusePolicy,null,null);
        }
        return new ResponseResult<>(0,"success",apiDetailList);
    }
}
