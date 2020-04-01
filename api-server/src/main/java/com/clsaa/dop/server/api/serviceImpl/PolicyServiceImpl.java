package com.clsaa.dop.server.api.serviceImpl;

import com.clsaa.dop.server.api.dao.entity.*;
import com.clsaa.dop.server.api.dao.repository.ServiceRepository;
import com.clsaa.dop.server.api.dao.repository.ServiceRouteRepository;
import com.clsaa.dop.server.api.dao.repository.TargetRepository;
import com.clsaa.dop.server.api.dao.repository.UpstreamRepository;
import com.clsaa.dop.server.api.module.configuration.WeightingPolicyConfig;
import com.clsaa.dop.server.api.module.kong.targetModule.KongTarget;
import com.clsaa.dop.server.api.module.kong.upstreamModule.KongUpstream;
import com.clsaa.dop.server.api.module.response.ResponseResult;
import com.clsaa.dop.server.api.module.response.policyDetail.routingPolicyDetail.RoutingPolicyDetail;
import com.clsaa.dop.server.api.module.response.policyDetail.routingPolicyDetail.ServiceDiscoveryPolicyDetail;
import com.clsaa.dop.server.api.module.response.policyDetail.routingPolicyDetail.WeightingPolicyDetail;
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

    @Autowired
    public PolicyServiceImpl(ServiceRouteRepository serviceRouteRepository, ServiceRepository serviceRepository,
                             ApiRestTemplate apiRestTemplate, UpstreamRepository upstreamRepository, TargetRepository targetRepository) {
        this.serviceRouteRepository = serviceRouteRepository;
        this.serviceRepository = serviceRepository;
        this.apiRestTemplate = apiRestTemplate;
        this.upstreamRepository = upstreamRepository;
        this.targetRepository = targetRepository;
    }

    @Override
    public ResponseResult<String> createServiceDiscoveryPolicy(String name, String description, String host, Long port, String path) {
        if (serviceRouteRepository.findByName(name) == null) {
            ServiceRoute serviceRoute = new ServiceRoute(name, description, host, port, path);
            serviceRouteRepository.saveAndFlush(serviceRoute);
            return new ResponseResult<>(0, "success", serviceRoute.getId());
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

                ServiceRoute serviceRoute = new ServiceRoute(name, description, kongUpstream.getName(), path, upstream);
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
                apiRestTemplate.modifyService(service.getId(), host, service.getName(), service.getTimeout(), "http", port, path);
            }
            serviceRoute.setName(name);
            serviceRoute.setDescription(description);
            serviceRoute.setHost(host);
            serviceRoute.setPort(port);
            serviceRoute.setPath(path);
            serviceRouteRepository.saveAndFlush(serviceRoute);
            return new ResponseResult(0, "success");
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
            if (serviceRoute.getUpstream() == null) {
                ServiceDiscoveryPolicyDetail serviceDiscoveryPolicyDetail = new ServiceDiscoveryPolicyDetail(serviceRoute.getId(),
                        serviceRoute.getName(), serviceRoute.getDescription(), serviceRoute.getHost(), serviceRoute.getPort(), serviceRoute.getPath());
                return new ResponseResult<>(0, "success", serviceDiscoveryPolicyDetail);
            } else {
                List<Target> targets = targetRepository.findByUpstream(serviceRoute.getUpstream());
                WeightingPolicyDetail weightingPolicyDetail = new WeightingPolicyDetail(serviceRoute, targets);
                return new ResponseResult<>(0, "success", weightingPolicyDetail);
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
                serviceRoutes = serviceRouteRepository.findByUpstreamIsNull();
                break;
            case "WeightingPolicy":
                serviceRoutes = serviceRouteRepository.findByUpstreamIsNotNull();
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
}
