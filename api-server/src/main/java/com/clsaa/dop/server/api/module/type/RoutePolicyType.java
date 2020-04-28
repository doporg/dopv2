package com.clsaa.dop.server.api.module.type;

public enum RoutePolicyType {
    DISCOVERY("ServiceDiscoveryPolicy"),
    WEIGHTING("WeightingPolicy")
    ;
    private String name;

    RoutePolicyType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static String getWeightingPolicyName(){
        return WEIGHTING.getName();
    }

    public static String  getServiceDiscoveryPolicyName(){
        return DISCOVERY.getName();
    }
}
