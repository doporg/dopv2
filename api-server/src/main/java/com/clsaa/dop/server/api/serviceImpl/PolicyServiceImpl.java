package com.clsaa.dop.server.api.serviceImpl;

import com.clsaa.dop.server.api.dao.*;
import com.clsaa.dop.server.api.module.po.*;
import com.clsaa.dop.server.api.module.configuration.WeightingPolicyConfig;
import com.clsaa.dop.server.api.module.kong.targetModule.KongTarget;
import com.clsaa.dop.server.api.module.kong.upstreamModule.KongUpstream;
import com.clsaa.dop.server.api.module.vo.request.policy.CurrentLimitPolicyParam;
import com.clsaa.dop.server.api.module.vo.response.CurrentLimitPolicyList;
import com.clsaa.dop.server.api.module.vo.response.ResponseResult;
import com.clsaa.dop.server.api.module.vo.response.RoutePolicyList;
import com.clsaa.dop.server.api.module.vo.response.policyDetail.CurrentLimitPolicyDetail;
import com.clsaa.dop.server.api.module.vo.response.policyDetail.routingPolicyDetail.RoutingPolicyDetail;
import com.clsaa.dop.server.api.module.vo.response.policyDetail.routingPolicyDetail.ServiceDiscoveryPolicyDetail;
import com.clsaa.dop.server.api.module.vo.response.policyDetail.routingPolicyDetail.WeightingPolicyDetail;
import com.clsaa.dop.server.api.module.type.RoutePolicyType;
import com.clsaa.dop.server.api.restTemplate.ApiRestTemplate;
import com.clsaa.dop.server.api.service.PolicyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    private LimitPolicyRepository limitPolicyRepository;

    @Autowired
    public PolicyServiceImpl(ServiceRouteRepository serviceRouteRepository, ServiceRepository serviceRepository,
                             ApiRestTemplate apiRestTemplate, UpstreamRepository upstreamRepository, TargetRepository targetRepository,
                             LimitPolicyRepository limitPolicyRepository) {
        this.serviceRouteRepository = serviceRouteRepository;
        this.serviceRepository = serviceRepository;
        this.apiRestTemplate = apiRestTemplate;
        this.upstreamRepository = upstreamRepository;
        this.targetRepository = targetRepository;
        this.limitPolicyRepository = limitPolicyRepository;
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
            serviceRoute.setPort(port);
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
                }else {
                    return new ResponseResult(3, "target update fail");
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
                    System.out.println("target: " +upstream.getId()+" "+ targetUrl+" "+weightingPolicyConfig.getWeights());
                    if (kongTarget != null) {
                        Target target = new Target(kongTarget.getId(), weightingPolicyConfig.getTargetHost(), weightingPolicyConfig.getTargetPort(), weightingPolicyConfig.getWeights(), upstream);
                        targetRepository.saveAndFlush(target);
                    }else {
                        return new ResponseResult(3, "target update error");
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
    public ResponseResult<RoutePolicyList> getRoutingPolicy(int pageNo, int pageSize, String type) {
        List<ServiceRoute> serviceRoutes;
        Sort sort = new Sort(Sort.Direction.DESC,"name");
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        switch (type) {
            case "All":
                serviceRoutes = serviceRouteRepository.findAll(pageable).getContent();
                break;
            case "ServiceDiscoveryPolicy":
                serviceRoutes = serviceRouteRepository.findAllByType(RoutePolicyType.getServiceDiscoveryPolicyName(),pageable).getContent();
                break;
            case "WeightingPolicy":
                serviceRoutes = serviceRouteRepository.findAllByType(RoutePolicyType.getWeightingPolicyName(),pageable).getContent();
                break;
            default:
                return new ResponseResult<>(1, "type not match");
        }
        if (pageSize == 0) throw new AssertionError();
        int num = serviceRoutes.size();
        int pageNum = (num-1)/pageSize+1;
        int current = pageNo<pageNum?pageNo:pageNum;
        RoutePolicyList routePolicyList = new RoutePolicyList(pageNum,current);
        for(ServiceRoute serviceRoute :serviceRoutes){
            routePolicyList.addRoutePolicy(new RoutingPolicyDetail(serviceRoute.getId(),serviceRoute.getName(),serviceRoute.getDescription(),serviceRoute.getType()));
        }
        return new ResponseResult<>(0, "success",routePolicyList);
    }

    @Override
    public ResponseResult<List<RoutingPolicyDetail>> searchRoutingPolicy(String value) {
        List<ServiceRoute> serviceRoutes = serviceRouteRepository.findByNameStartingWith(value);
        List<RoutingPolicyDetail> routingPolicyDetails = new LinkedList<>();
        for (ServiceRoute serviceRoute : serviceRoutes) {
            routingPolicyDetails.add(new RoutingPolicyDetail(serviceRoute.getId(),serviceRoute.getName(),serviceRoute.getDescription(),serviceRoute.getType()));
        }
        return new ResponseResult<>(0, "success",routingPolicyDetails);
    }

    @Override
    public ResponseResult<String> createCurrentLimitPolicy(CurrentLimitPolicyParam policyParams) {
        if (limitPolicyRepository.findByName(policyParams.getName())==null){
            LimitPolicy limitPolicy = new LimitPolicy(policyParams.getName(),policyParams.getDescription(),policyParams.getSecond(),policyParams.getMinute(),policyParams.getHour(),policyParams.getDay());
            limitPolicyRepository.saveAndFlush(limitPolicy);
            return new ResponseResult<>(0, "success", limitPolicy.getId());
        }else {
            return new ResponseResult<>(2, "name repeat");
        }
    }

    @Override
    public ResponseResult modifyCurrentLimitPolicy(CurrentLimitPolicyParam policyParams, String policyId) {
        LimitPolicy policy = limitPolicyRepository.findLimitPolicyById(policyId);
        if (policy!=null){
            policy.setName(policyParams.getName());
            policy.setSecond(policyParams.getSecond());
            policy.setMinute(policyParams.getMinute());
            policy.setHour(policyParams.getHour());
            policy.setDay(policyParams.getDay());
            limitPolicyRepository.saveAndFlush(policy);

            List<Service> services = serviceRepository.findByLimitPolicy(policy);
            for(Service service:services){
                if (!updateServiceCurrentLimitPolicy(service,policy)){
                    return new ResponseResult<>(2, "update currentPolicy fail");
                }
            }
                return new ResponseResult<>(0, "success");
        }else {
            return new ResponseResult<>(1, "policy not found");
        }
    }

    @Override
    public ResponseResult<String> deleteCurrentLimitPolicy(String policyId) {
        LimitPolicy policy = limitPolicyRepository.findLimitPolicyById(policyId);
        if (policy!=null){
            if (serviceRepository.findByLimitPolicy(policy).size()==0){
                limitPolicyRepository.delete(policy);
                return new ResponseResult<>(0, "success");
            }else {
                return new ResponseResult<>(2, "policy is used");
            }
        }else {
            return new ResponseResult<>(1, "policy not found");
        }
    }

    @Override
    public ResponseResult<CurrentLimitPolicyList> getCurrentLimitPolicies(int pageNo, int pageSize) {
        Sort sort = new Sort(Sort.Direction.DESC,"name");
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        List<LimitPolicy> currentLimitPolicies = limitPolicyRepository.findAll(pageable).getContent();
        if (pageSize == 0) throw new AssertionError();
        int num = currentLimitPolicies.size();
        int pageNum = (num-1)/pageSize+1;
        int current = pageNo<pageNum?pageNo:pageNum;

        CurrentLimitPolicyList currentLimitPolicyList = new CurrentLimitPolicyList(pageNum,current);
        for(LimitPolicy limitPolicy:currentLimitPolicies){
            currentLimitPolicyList.addCurrentLimitPolicy(new CurrentLimitPolicyDetail(limitPolicy.getId(), limitPolicy.getName(), limitPolicy.getDescription(),
                    limitPolicy.getSecond(), limitPolicy.getMinute(), limitPolicy.getHour(), limitPolicy.getDay()));
        }
        return new ResponseResult<>(0, "success",currentLimitPolicyList);
    }

    @Override
    public ResponseResult<CurrentLimitPolicyDetail> getCurrentLimitPolicyDetail(String policyId) {
        LimitPolicy limitPolicy = limitPolicyRepository.findLimitPolicyById(policyId);
        if (limitPolicy !=null){
            return new ResponseResult<>(0, "success",new CurrentLimitPolicyDetail(limitPolicy.getId(), limitPolicy.getName(), limitPolicy.getDescription(), limitPolicy.getSecond(), limitPolicy.getMinute(), limitPolicy.getHour(), limitPolicy.getDay()));
        }else {
            return new ResponseResult<>(1, "policy not found");
        }
    }

    @Override
    public ResponseResult<List<CurrentLimitPolicyDetail>> searchCurrentLimitPolicy(String value) {
        List<LimitPolicy> currentLimitPolicies = limitPolicyRepository.findByNameStartingWith(value);
        List<CurrentLimitPolicyDetail> currentLimitPolicyDetails = new LinkedList<>();
        for (LimitPolicy limitPolicy : currentLimitPolicies) {
            currentLimitPolicyDetails.add(new CurrentLimitPolicyDetail(limitPolicy.getId(), limitPolicy.getName(), limitPolicy.getDescription(),
                    limitPolicy.getSecond(), limitPolicy.getMinute(), limitPolicy.getHour(), limitPolicy.getDay()));
        }
        return new ResponseResult<>(0, "success",currentLimitPolicyDetails);
    }

    public boolean updateServiceCurrentLimitPolicy(Service service, LimitPolicy limitPolicy){
        String pluginId = service.getRateLimitingPluginId();
        if (!pluginId.equals("")){
            apiRestTemplate.deletePlugin(pluginId);
        }
        if (limitPolicy ==null){
            service.setRateLimitingPluginId("");
            service.setLimitPolicy(null);
            serviceRepository.saveAndFlush(service);
            return true;
        }else {
            pluginId =  apiRestTemplate.createCurrentLimitPolicy(service.getId(), limitPolicy.getSecond(), limitPolicy.getMinute(), limitPolicy.getHour(), limitPolicy.getDay());
            if (pluginId!=null){
                service.setRateLimitingPluginId(pluginId);
                service.setLimitPolicy(limitPolicy);
                serviceRepository.saveAndFlush(service);
                return true;
            }else {
                return false;
            }
        }
    }
}
