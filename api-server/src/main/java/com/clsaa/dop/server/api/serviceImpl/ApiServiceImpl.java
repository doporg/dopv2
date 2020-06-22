package com.clsaa.dop.server.api.serviceImpl;

import com.clsaa.dop.server.api.dao.LimitPolicyRepository;
import com.clsaa.dop.server.api.dao.RouteRepository;
import com.clsaa.dop.server.api.dao.ServiceRepository;
import com.clsaa.dop.server.api.dao.ServiceRouteRepository;
import com.clsaa.dop.server.api.module.kong.upstreamModule.UpstreamHealth;
import com.clsaa.dop.server.api.module.po.*;
import com.clsaa.dop.server.api.module.kong.routeModule.KongRoute;
import com.clsaa.dop.server.api.module.kong.serviceModule.KongService;
import com.clsaa.dop.server.api.module.kong.upstreamModule.KongUpstream;
import com.clsaa.dop.server.api.module.vo.request.lifeCycle.CreateApiParams;
import com.clsaa.dop.server.api.module.vo.request.lifeCycle.FusePolicy;
import com.clsaa.dop.server.api.module.vo.request.lifeCycle.ModifyApiParams;
import com.clsaa.dop.server.api.module.vo.response.ApiDetail;
import com.clsaa.dop.server.api.module.vo.response.ApiList;
import com.clsaa.dop.server.api.module.vo.response.ResponseResult;
import com.clsaa.dop.server.api.module.vo.response.policyDetail.ApiInfo;
import com.clsaa.dop.server.api.module.vo.response.policyDetail.CurrentLimitPolicyDetail;
import com.clsaa.dop.server.api.module.vo.response.policyDetail.routingPolicyDetail.RoutingPolicyDetail;
import com.clsaa.dop.server.api.restTemplate.ApiRestTemplate;
import com.clsaa.dop.server.api.service.ApiService;
import com.clsaa.dop.server.api.service.PolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@org.springframework.stereotype.Service
public class ApiServiceImpl implements ApiService {

    private ServiceRepository serviceRepository;
    private RouteRepository routeRepository;
    private ServiceRouteRepository serviceRouteRepository;
    private LimitPolicyRepository limitPolicyRepository;
    private PolicyService policyService;
    private ApiRestTemplate apiRestTemplate;

    @Autowired
    public ApiServiceImpl(ServiceRepository serviceRepository, RouteRepository routeRepository, ServiceRouteRepository serviceRouteRepository,
                          ApiRestTemplate apiRestTemplate, LimitPolicyRepository limitPolicyRepository, PolicyService policyService) {
        this.serviceRepository = serviceRepository;
        this.routeRepository = routeRepository;
        this.serviceRouteRepository = serviceRouteRepository;
        this.limitPolicyRepository = limitPolicyRepository;
        this.apiRestTemplate = apiRestTemplate;
        this.policyService = policyService;
    }

    @Override
    public ResponseResult<String> createApi(CreateApiParams createApiParams) {

        //查看是否有同名或相同路径的service
        if (serviceRepository.findByName(createApiParams.getName())==null&&routeRepository.findByRequestPath(createApiParams.getRequestPath())==null){
            String upstreamId = createApiParams.getRoutingPolicyId();
            ServiceRoute serviceRoute = serviceRouteRepository.findServiceRouteById(upstreamId);
            FusePolicy fusePolicy = createApiParams.getFusePolicy();
            //查看负载均衡策略是否存在
            if(serviceRoute !=null){

                //在Kong服务器中创建Service对象
                KongService kongService = apiRestTemplate.createService(serviceRoute.getHost(),createApiParams.getName(),
                        createApiParams.getTimeout(),"http", serviceRoute.getPort(),serviceRoute.getPath());
                //修改Upstream对象
                KongUpstream kongUpstream = apiRestTemplate.modifyFusePolicy(serviceRoute.getHost(),fusePolicy);

                if (kongService!=null&&kongUpstream!=null){

                    //API数据持久化
                    Service service = new Service(kongService.getId(),createApiParams.getName(),createApiParams.getDescription(),
                            createApiParams.getTimeout(),fusePolicy.isEnable(),
                            fusePolicy.getFuseDetectionRing(),fusePolicy.getCriticalFusingFailureRate(),fusePolicy.getFuseDuration(),
                            fusePolicy.getReplyDetectionRingSize(), serviceRoute);
                    serviceRepository.saveAndFlush(service);

                    Route route = new Route(false,createApiParams.getRequestMethod(),createApiParams.getRequestPath(),service);
                    routeRepository.saveAndFlush(route);

                    //更新缓存策略
                    if (updateCachePolicy(createApiParams.isCaching(),createApiParams.getCachingTime().intValue(),service)){
                        //更新流控策略
                        LimitPolicy limitPolicy = limitPolicyRepository.findLimitPolicyById(createApiParams.getCurrentLimitPolicyId());
                        if (policyService.updateServiceCurrentLimitPolicy(service, limitPolicy)){
                            //创建成功，返回api的ID
                            return new ResponseResult<>(0,"success",service.getId());
                        }else {
                            return new ResponseResult<>(5,"current limit policy update error",service.getId());
                        }
                    }else {
                        return new ResponseResult<>(4,"cache policy update error",service.getId());
                    }
                }else {
                    //Kong服务异常
                    return new ResponseResult<>(3,"fail");
                }
            }else {
                //路由策略缺失
                return new ResponseResult<>(2,"no route policy");
            }
        }else{
            //重名
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
    public ResponseResult  deleteApi(String apiId) {
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
    public ResponseResult modifyApi(String apiId,ModifyApiParams modifyApiParams) {
        //查看service是否存在
        Service service = serviceRepository.findServiceById(apiId);
        Route route = routeRepository.findRouteByServiceId(apiId);
        if (service!=null&&route!=null){
            String upstreamId = modifyApiParams.getRoutingPolicy();
            ServiceRoute serviceRoute = serviceRouteRepository.findServiceRouteById(upstreamId);
            FusePolicy fusePolicy = modifyApiParams.getFusePolicy();

            //查看路由策略是否存在
            if(serviceRoute !=null){
                //更新kong
                KongService kongService = apiRestTemplate.modifyService(apiId, serviceRoute.getHost(),modifyApiParams.getName(),modifyApiParams.getTimeout(),"http", 80L,serviceRoute.getPath());
                KongUpstream kongUpstream = apiRestTemplate.modifyFusePolicy(serviceRoute.getHost(),fusePolicy);
                if (kongService!=null&&kongUpstream!=null){
                    //更新数据库
                    service.setName(modifyApiParams.getName());
                    service.setDescription(modifyApiParams.getDescription());
                    service.setTimeout(modifyApiParams.getTimeout());
                    service.setFuse(fusePolicy.isEnable());
                    service.setFuseDetectionRing(fusePolicy.getFuseDetectionRing());
                    service.setCriticalFusingFailureRate(fusePolicy.getCriticalFusingFailureRate());
                    service.setFuseDuration(fusePolicy.getFuseDuration());
                    service.setReplyDetectionRingSize(fusePolicy.getReplyDetectionRingSize());
                    serviceRepository.saveAndFlush(service);

                    route.setRequestMethod(modifyApiParams.getRequestMethod());
                    route.setRequestPath(modifyApiParams.getRequestPath());
                    routeRepository.saveAndFlush(route);

                    //更新缓存策略
                    if (updateCachePolicy(modifyApiParams.isCaching(),modifyApiParams.getCachingTime().intValue(),service)){
                        //更新流控策略
                        LimitPolicy limitPolicy = limitPolicyRepository.findLimitPolicyById(modifyApiParams.getCurrentLimitPolicy());
                        if (policyService.updateServiceCurrentLimitPolicy(service, limitPolicy)){
                            //更新请求路径
                            if (route.isOnline()){
                                if (apiRestTemplate.modifyRoute(route.getKongRouteId(),route.getRequestMethod(),route.getRequestPath(),apiId)!=null){
                                    return new ResponseResult<>(0,"success");
                                }else {
                                    return new ResponseResult<>(4,"route modify error");
                                }
                            }else {
                                return new ResponseResult<>(0,"success");
                            }
                        }else {
                            return new ResponseResult<>(5,"current limit policy update error",service.getId());
                        }
                    }else {
                        return new ResponseResult<>(4,"cache policy update error",service.getId());
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
            LimitPolicy limitPolicy = service.getLimitPolicy();
            Upstream upstream = serviceRoute.getUpstream();
            FusePolicy fusePolicy = new FusePolicy(service.isFuse(),service.getFuseDetectionRing(),service.getCriticalFusingFailureRate(),
                    service.getFuseDuration(),service.getReplyDetectionRingSize());
            RoutingPolicyDetail routingPolicyDetail = new RoutingPolicyDetail(serviceRoute.getId(),serviceRoute.getName(),serviceRoute.getDescription(),serviceRoute.getType());
            UpstreamHealth upstreamHealth = apiRestTemplate.getUpstreamHealth(upstream.getId());

            CurrentLimitPolicyDetail currentLimitPolicyDetail = null;
            if (limitPolicy !=null){
                currentLimitPolicyDetail = new CurrentLimitPolicyDetail(limitPolicy.getId(), limitPolicy.getName(), limitPolicy.getDescription(), limitPolicy.getSecond(), limitPolicy.getMinute(), limitPolicy.getHour(), limitPolicy.getDay());
            }
            return new ResponseResult<>(0,"success",new ApiDetail(apiId,service.getName(),service.getDescription(),upstreamHealth.getHealthData(),
                    route.isOnline(),route.getRequestMethod(),route.getRequestPath(),service.getTimeout(),service.isCaching(),
                    service.getCachingTime(),fusePolicy,routingPolicyDetail,currentLimitPolicyDetail));
        }else{
            return new ResponseResult<>(1,"no service");
        }
    }

    @Override
    public ResponseResult<ApiList> getApiList(int pageNo, int pageSize, String apiType) {
        List<Route> routes;
        Sort sort = new Sort(Sort.Direction.DESC,"id");
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        switch (apiType) {
            case "online":
                routes = routeRepository.findAllByOnline(true,pageable).getContent();
                break;
            case "offline":
                routes = routeRepository.findAllByOnline(false,pageable).getContent();
                break;
            default:
                routes = routeRepository.findAll(pageable).getContent();
                break;
        }
        if (pageSize == 0) throw new AssertionError();
        int num = routes.size();
        int pageNum = (num-1)/pageSize+1;
        int current = pageNo<pageNum?pageNo:pageNum;
        ApiList apiList = new ApiList(pageNum,current);
        for (Route route : routes) {
            Service service = route.getService();
            Upstream upstream = service.getServiceRoute().getUpstream();
            UpstreamHealth upstreamHealth = apiRestTemplate.getUpstreamHealth(upstream.getId());
            apiList.addApiInfo(new ApiInfo(service.getId(), service.getName(), service.getDescription(), route.getRequestPath(), "user", upstreamHealth.getHealthData(), route.isOnline()));
        }
        return new ResponseResult<>(0,"success",apiList);
    }

    private boolean updateCachePolicy(boolean enable,int ttl,Service service){
        if (enable){
            String pluginId = service.getProxyCachePluginId();
            if (pluginId.equals("")){
                pluginId = apiRestTemplate.createCachePolicy(service.getId(),ttl);
                if (pluginId!=null){
                    service.setProxyCachePluginId(pluginId);
                }else {
                    return false;
                }
            }else {
                if (!apiRestTemplate.updateCachePolicy(pluginId,service.getId(),ttl)){
                    return false;
                }
            }
            service.setCachingTime((long) ttl);
        }else {
            apiRestTemplate.deletePlugin(service.getProxyCachePluginId());
            service.setProxyCachePluginId("");
        }
        service.setCaching(enable);
        serviceRepository.saveAndFlush(service);
        return true;
    }
}
