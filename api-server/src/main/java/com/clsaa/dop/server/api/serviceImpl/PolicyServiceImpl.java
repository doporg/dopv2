package com.clsaa.dop.server.api.serviceImpl;

import com.clsaa.dop.server.api.dao.*;
import com.clsaa.dop.server.api.module.po.*;
import com.clsaa.dop.server.api.module.configuration.WeightingPolicyConfig;
import com.clsaa.dop.server.api.module.kong.targetModule.KongTarget;
import com.clsaa.dop.server.api.module.kong.upstreamModule.KongUpstream;
import com.clsaa.dop.server.api.module.vo.response.ResponseResult;
import com.clsaa.dop.server.api.module.vo.response.policyDetail.CurrentLimitPolicyDetail;
import com.clsaa.dop.server.api.module.vo.response.policyDetail.routingPolicyDetail.RoutingPolicyDetail;
import com.clsaa.dop.server.api.module.vo.response.policyDetail.routingPolicyDetail.ServiceDiscoveryPolicyDetail;
import com.clsaa.dop.server.api.module.vo.response.policyDetail.routingPolicyDetail.WeightingPolicyDetail;
import com.clsaa.dop.server.api.module.type.RoutePolicyType;
import com.clsaa.dop.server.api.restTemplate.ApiRestTemplate;
import com.clsaa.dop.server.api.service.PolicyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;


@org.springframework.stereotype.Service
public class PolicyServiceImpl implements PolicyService {
    private ServiceRouteRepository serviceRouteRepository;
    private ServiceRepository serviceRepository;
    private ApiRestTemplate apiRestTemplate;
    private UpstreamRepository upstreamRepository;
    private TargetRepository targetRepository;
    private CurrentLimitPolicyRepository currentLimitPolicyRepository;

    @Autowired
    public PolicyServiceImpl(ServiceRouteRepository serviceRouteRepository, ServiceRepository serviceRepository,
                             ApiRestTemplate apiRestTemplate, UpstreamRepository upstreamRepository, TargetRepository targetRepository,
                             CurrentLimitPolicyRepository currentLimitPolicyRepository) {
        this.serviceRouteRepository = serviceRouteRepository;
        this.serviceRepository = serviceRepository;
        this.apiRestTemplate = apiRestTemplate;
        this.upstreamRepository = upstreamRepository;
        this.targetRepository = targetRepository;
        this.currentLimitPolicyRepository = currentLimitPolicyRepository;
    }

    @Override
    public ResponseResult<String> createServiceDiscoveryPolicy(String name, String description, String host, Long port, String path) {
        if (serviceRouteRepository.findByName(name) == null&&upstreamRepository.findUpstreamByName(host)==null) {
            KongUpstream kongUpstream = apiRestTemplate.createUpstream(host);
            if (kongUpstream != null) {
                String upstreamId = kongUpstream.getId();
                Upstream upstream = new Upstream(upstreamId, host);
                upstreamRepository.saveAndFlush(upstream);

                ServiceRoute serviceRoute = new ServiceRoute(name, description,RoutePolicyType.getServiceDiscoveryPolicyName(), kongUpstream.getName(), path, upstream);
                serviceRouteRepository.saveAndFlush(serviceRoute);

                String targetUrl = host + ":" + port;
                KongTarget kongTarget = apiRestTemplate.createTarget(upstreamId, targetUrl);
                if (kongTarget != null) {
                    Target target = new Target(kongTarget.getId(),host, port,upstream);
                    targetRepository.saveAndFlush(target);
                }
                return new ResponseResult<>(0, "success", serviceRoute.getId());
            } else {
                return new ResponseResult<>(2, "create fail");
            }
        } else {
            return new ResponseResult<>(1, "name repeat");
        }
    }

    @Override
    @Transactional
    public ResponseResult<String> createWeightingPolicy(String name, String description, String algorithm, String hashOn, String header, String path, List<WeightingPolicyConfig> configs) {
        if (serviceRouteRepository.findByName(name) == null) {
            String upstreamName = UUID.randomUUID().toString().replaceAll("-", "");
            KongUpstream kongUpstream = apiRestTemplate.createUpstream(upstreamName, algorithm, hashOn, header);
            if (kongUpstream != null) {
                String upstreamId = kongUpstream.getId();
                Upstream upstream = new Upstream(upstreamId, upstreamName, algorithm, hashOn, header);
                upstreamRepository.saveAndFlush(upstream);

                ServiceRoute serviceRoute = new ServiceRoute(name, description,RoutePolicyType.getWeightingPolicyName(), kongUpstream.getName(), path, upstream);
                serviceRouteRepository.saveAndFlush(serviceRoute);

                for (WeightingPolicyConfig weightingPolicyConfig : configs) {
                    String targetUrl = weightingPolicyConfig.getTargetHost() + ":" + weightingPolicyConfig.getTargetPort();
                    KongTarget kongTarget = apiRestTemplate.createTarget(upstreamId, targetUrl, weightingPolicyConfig.getWeights());
                    if (kongTarget != null) {
                        Target target = new Target(kongTarget.getId(), weightingPolicyConfig.getTargetHost(), weightingPolicyConfig.getTargetPort(), weightingPolicyConfig.getWeights(), upstream);
                        targetRepository.saveAndFlush(target);
                    }
                }
                return new ResponseResult<>(0, "success", serviceRoute.getId());
            } else {
                return new ResponseResult<>(2, "create fail");
            }
        } else {
            return new ResponseResult<>(1, "name repeat");
        }
    }

    @Override
    @Transactional
    public ResponseResult deleteRoutingPolicy(String policyId) {
        ServiceRoute serviceRoute = serviceRouteRepository.findServiceRouteById(policyId);
        if (serviceRoute != null) {
            List<Service> services = serviceRepository.findByServiceRoute(serviceRoute);
            if (services.size() == 0) {
                serviceRouteRepository.delete(serviceRoute);
                if (serviceRoute.getUpstream() != null) {
                    Upstream upstream = serviceRoute.getUpstream();
                    if (apiRestTemplate.deleteUpstream(upstream.getId())) {
                        targetRepository.deleteByUpstream(upstream);
                        upstreamRepository.delete(upstream);
                        return new ResponseResult(0, "success");
                    } else {
                        return new ResponseResult(3, "delete error");
                    }
                } else {
                    return new ResponseResult(0, "success");
                }
            } else {
                return new ResponseResult(2, "be used");
            }
        } else {
            return new ResponseResult(1, "not found");
        }
    }

    @Override
    public ResponseResult modifyServiceDiscoveryPolicy(String policyId, String name, String description, String host, Long port, String path) {
        ServiceRoute serviceRoute = serviceRouteRepository.findServiceRouteById(policyId);
        if (serviceRoute != null) {
            List<Service> services = serviceRepository.findByServiceRoute(serviceRoute);
            for (Service service : services) {
                apiRestTemplate.modifyService(service.getId(), serviceRoute.getHost(), service.getName(), service.getTimeout(), "http", serviceRoute.getPort(), path);
            }
            serviceRoute.setName(name);
            serviceRoute.setHost(host);
            serviceRoute.setDescription(description);
            serviceRoute.setPath(path);
            serviceRouteRepository.saveAndFlush(serviceRoute);

            Upstream upstream = serviceRoute.getUpstream();
            KongUpstream kongUpstream = apiRestTemplate.modifyUpstream(upstream.getId(),host);
            if (kongUpstream != null) {
                upstream.setName(host);
                upstreamRepository.saveAndFlush(upstream);

                List<Target> targets = targetRepository.findByUpstream(upstream);
                for (Target target : targets) {
                    apiRestTemplate.deleteTarget(upstream.getId(), target.getId());
                    targetRepository.deleteById(target.getId());
                }
                String targetUrl = host + ":" + port;
                KongTarget kongTarget = apiRestTemplate.createTarget(upstream.getId(), targetUrl);
                if (kongTarget != null) {
                    Target target = new Target(kongTarget.getId(), host, port, upstream);
                    targetRepository.saveAndFlush(target);
                }


                return new ResponseResult(0, "success");
            } else {
                return new ResponseResult(2, "service error");
            }
        } else {
            return new ResponseResult(1, "not found");
        }
    }

    @Override
    @Transactional()
    public ResponseResult modifyWeightingPolicy(String policyId, String name, String description, String algorithm, String hashOn, String header, String path, List<WeightingPolicyConfig> configs) {
        ServiceRoute serviceRoute = serviceRouteRepository.findServiceRouteById(policyId);
        if (serviceRoute != null) {
            List<Service> services = serviceRepository.findByServiceRoute(serviceRoute);
            for (Service service : services) {
                apiRestTemplate.modifyService(service.getId(), serviceRoute.getHost(), service.getName(), service.getTimeout(), "http", serviceRoute.getPort(), path);
            }
            serviceRoute.setName(name);
            serviceRoute.setDescription(description);
            serviceRoute.setPath(path);
            serviceRouteRepository.saveAndFlush(serviceRoute);

            Upstream upstream = serviceRoute.getUpstream();
            KongUpstream kongUpstream = apiRestTemplate.modifyUpstream(upstream.getId(), algorithm, hashOn, header);
            if (kongUpstream != null) {
                upstream.setAlgorithm(algorithm);
                upstream.setHash_on(hashOn);
                upstream.setHeader(header);
                upstreamRepository.saveAndFlush(upstream);

                //修改target
                List<Target> targets = targetRepository.findByUpstream(upstream);
                for (Target target : targets) {
                    apiRestTemplate.deleteTarget(upstream.getId(), target.getId());
                    targetRepository.deleteById(target.getId());
                }
                for (WeightingPolicyConfig weightingPolicyConfig : configs) {
                    String targetUrl = weightingPolicyConfig.getTargetHost() + ":" + weightingPolicyConfig.getTargetPort();
                    KongTarget kongTarget = apiRestTemplate.createTarget(upstream.getId(), targetUrl, weightingPolicyConfig.getWeights());
                    if (kongTarget != null) {
                        Target target = new Target(kongTarget.getId(), weightingPolicyConfig.getTargetHost(), weightingPolicyConfig.getTargetPort(), weightingPolicyConfig.getWeights(), upstream);
                        targetRepository.saveAndFlush(target);
                    }
                }
                return new ResponseResult(0, "success");
            } else {
                return new ResponseResult(2, "service error");
            }
        } else {
            return new ResponseResult(1, "not found");
        }
    }

    @Override
    public ResponseResult<RoutingPolicyDetail> getRoutingPolicyDetail(String policyId) {
        ServiceRoute serviceRoute = serviceRouteRepository.findServiceRouteById(policyId);
        if (serviceRoute != null) {
            if (serviceRoute.getType().equals(RoutePolicyType.getServiceDiscoveryPolicyName())) {
                ServiceDiscoveryPolicyDetail serviceDiscoveryPolicyDetail = new ServiceDiscoveryPolicyDetail(serviceRoute.getId(),
                        serviceRoute.getName(), serviceRoute.getDescription(), serviceRoute.getHost(), serviceRoute.getPort(), serviceRoute.getPath());
                return new ResponseResult<>(0, "success", serviceDiscoveryPolicyDetail);
            } else if(serviceRoute.getType().equals(RoutePolicyType.getWeightingPolicyName())){
                List<Target> targets = targetRepository.findByUpstream(serviceRoute.getUpstream());
                WeightingPolicyDetail weightingPolicyDetail = new WeightingPolicyDetail(serviceRoute, targets);
                return new ResponseResult<>(0, "success", weightingPolicyDetail);
            }else {
                return new ResponseResult<>(2, "type not found");
            }
        } else {
            return new ResponseResult<>(1, "not found");
        }
    }

    @Override
    public ResponseResult<List<RoutingPolicyDetail>> getRoutingPolicy(String type) {
        List<ServiceRoute> serviceRoutes;
        switch (type) {
            case "All":
                serviceRoutes = serviceRouteRepository.findAll();
                break;
            case "ServiceDiscoveryPolicy":
                serviceRoutes = serviceRouteRepository.findByType(RoutePolicyType.getServiceDiscoveryPolicyName());
                break;
            case "WeightingPolicy":
                serviceRoutes = serviceRouteRepository.findByType(RoutePolicyType.getWeightingPolicyName());
                break;
            default:
                return new ResponseResult<>(1, "type not match");
        }
        List<RoutingPolicyDetail> routingPolicyDetails = new LinkedList<>();
        for (ServiceRoute serviceRoute : serviceRoutes) {
            routingPolicyDetails.add(new RoutingPolicyDetail(serviceRoute.getId(),serviceRoute.getName(),serviceRoute.getDescription(),serviceRoute.getType()));
        }
        return new ResponseResult<>(0, "success",routingPolicyDetails);
    }

    @Override
    public ResponseResult<String> createCurrentLimitPolicy(String name, String cycle, int requests,String serviceId) {
        Service service = serviceRepository.findServiceById(serviceId);
        if (service!=null){
            if (currentLimitPolicyRepository.findByNameAndService(name,service)==null){
                CurrentLimitPolicy currentLimitPolicy = new CurrentLimitPolicy(name,cycle,requests,service);
                currentLimitPolicyRepository.saveAndFlush(currentLimitPolicy);
                if (updateServiceCurrentLimitPolicy(service)){
                    return new ResponseResult<>(0, "success",currentLimitPolicy.getId());
                }else {
                    return new ResponseResult<>(3, "currentPolicy update fail");
                }
            }else {
                return new ResponseResult<>(2, "name repeat");
            }
        }else {
            return new ResponseResult<>(1, "service not found");
        }
    }

    @Override
    public ResponseResult modifyCurrentLimitPolicy(String name, String cycle, int requests,String policyId) {
        CurrentLimitPolicy policy = currentLimitPolicyRepository.findCurrentLimitPolicyById(policyId);
        if (policy!=null){
            policy.setName(name);
            policy.setCircle(cycle);
            policy.setTime(requests);
            currentLimitPolicyRepository.saveAndFlush(policy);
            if (updateServiceCurrentLimitPolicy(policy.getService())){
                return new ResponseResult<>(0, "success");
            }else {
                return new ResponseResult<>(2, "update currentPolicy fail");
            }
        }else {
            return new ResponseResult<>(1, "policy not found");
        }
    }

    @Override
    public ResponseResult<String> deleteCurrentLimitPolicy(String policyId) {
        CurrentLimitPolicy policy = currentLimitPolicyRepository.findCurrentLimitPolicyById(policyId);
        if (policy!=null){
            currentLimitPolicyRepository.delete(policy);
            Service service = policy.getService();
            updateServiceCurrentLimitPolicy(service);
            return new ResponseResult<>(0, "success");
        }else {
            return new ResponseResult<>(1, "policy not found");
        }
    }

    @Override
    public ResponseResult<List<CurrentLimitPolicyDetail>> getCurrentLimitPolicies(String serviceId) {
        Service service = serviceRepository.findServiceById(serviceId);
        if (service!=null){
            List<CurrentLimitPolicy> currentLimitPolicies = currentLimitPolicyRepository.findByService(service);
            List<CurrentLimitPolicyDetail> currentLimitPolicyDetails = new LinkedList<>();
            for(CurrentLimitPolicy currentLimitPolicy:currentLimitPolicies){
                currentLimitPolicyDetails.add(new CurrentLimitPolicyDetail(currentLimitPolicy.getId(),currentLimitPolicy.getName(),currentLimitPolicy.getCircle(),currentLimitPolicy.getTime()));
            }
            return new ResponseResult<>(0, "success",currentLimitPolicyDetails);
        }else {
            return new ResponseResult<>(1, "service not found");
        }
    }

    private boolean updateServiceCurrentLimitPolicy(Service service){
        List<CurrentLimitPolicy> currentLimitPolicies = currentLimitPolicyRepository.findByService(service);
        if (currentLimitPolicies.size()==0){
            //无流控策略
            apiRestTemplate.deletePlugin(service.getRateLimitingPluginId());
            service.setRateLimitingPluginId("");
            serviceRepository.saveAndFlush(service);
            return true;
        }else {
            //有流控策略，进行更新
            int second = 0;
            int minute = 0;
            int hour = 0;
            int day = 0;
            for(CurrentLimitPolicy currentLimitPolicy :currentLimitPolicies){
                if (currentLimitPolicy.getCircle().equals("second")&&(second==0||second>currentLimitPolicy.getTime())){
                    second = currentLimitPolicy.getTime();
                }else if (currentLimitPolicy.getCircle().equals("minute")&&(minute==0||minute>currentLimitPolicy.getTime())){
                    minute = currentLimitPolicy.getTime();
                }else if (currentLimitPolicy.getCircle().equals("hour")&&(hour==0||hour>currentLimitPolicy.getTime())){
                    hour = currentLimitPolicy.getTime();
                }else if (currentLimitPolicy.getCircle().equals("day")&&(day==0||day>currentLimitPolicy.getTime())){
                    day = currentLimitPolicy.getTime();
                }
            }
            String pluginId = service.getRateLimitingPluginId();
            if (service.getRateLimitingPluginId().equals("")){
                pluginId =  apiRestTemplate.createCurrentLimitPolicy(service.getId(),second,minute,hour,day);
                if (pluginId!=null){
                    service.setRateLimitingPluginId(pluginId);
                    serviceRepository.saveAndFlush(service);
                    return true;
                }else {
                    return false;
                }
            }else {
                return apiRestTemplate.updateCurrentLimitPolicy(pluginId,service.getId(),second,minute,hour,day);
            }
        }
    }
}