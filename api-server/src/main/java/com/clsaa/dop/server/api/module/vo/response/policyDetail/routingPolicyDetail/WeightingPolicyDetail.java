package com.clsaa.dop.server.api.module.vo.response.policyDetail.routingPolicyDetail;

import com.clsaa.dop.server.api.module.po.ServiceRoute;
import com.clsaa.dop.server.api.module.po.Target;
import com.clsaa.dop.server.api.module.po.Upstream;
import com.clsaa.dop.server.api.module.configuration.WeightingPolicyConfig;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.LinkedList;
import java.util.List;

@ApiModel(description = "权重策略详情")
public class WeightingPolicyDetail extends RoutingPolicyDetail{
    @ApiModelProperty(value = "configuration",dataType = "Configuration")
    private List<WeightingPolicyConfig> configurations;

    @ApiModelProperty(value = "algorithm",dataType = "String",allowableValues = "round-robin,hash,least-connections")
    private String algorithm;

    @ApiModelProperty(value = "hashOn",dataType = "String",allowableValues = "header,ip")
    private String hashOn;

    @ApiModelProperty(value = "header",dataType = "String",example = "host")
    private String header;

    @ApiModelProperty(value = "path",dataType = "String",example = "/test")
    private String path;

    public WeightingPolicyDetail() {
    }


    public WeightingPolicyDetail(ServiceRoute serviceRoute,List<Target> targets) {
        super(serviceRoute.getId(), serviceRoute.getName(), serviceRoute.getDescription(),"WeightingPolicy");
        this.path = serviceRoute.getPath();
        Upstream upstream = serviceRoute.getUpstream();
        this.algorithm = upstream.getAlgorithm();
        this.hashOn = upstream.getHash_on();
        this.header = upstream.getHeader();
        this.configurations = new LinkedList<>();
        for(Target target:targets){
            configurations.add(new WeightingPolicyConfig(target.getHost(),target.getPort(),target.getWeights()));
        }
    }

    public List<WeightingPolicyConfig> getConfigurations() {
        return configurations;
    }

    public void addConfiguration(String targetHost, int targetPort, int weights){
        WeightingPolicyConfig configuration = new WeightingPolicyConfig(targetHost,targetPort,weights);
        configurations.add(configuration);
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public String getHashOn() {
        return hashOn;
    }

    public String getHeader() {
        return header;
    }

    public String getPath() {
        return path;
    }
}

