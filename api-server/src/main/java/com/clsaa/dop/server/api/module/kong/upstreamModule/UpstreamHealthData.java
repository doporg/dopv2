package com.clsaa.dop.server.api.module.kong.upstreamModule;

public class UpstreamHealthData {
    private String health;
    private String id;

    public UpstreamHealthData() {
    }

    public UpstreamHealthData(String health, String id) {
        this.health = health;
        this.id = id;
    }

    public String getHealth() {
        return health;
    }

    public String getId() {
        return id;
    }

    public boolean isHealthy(){
        return health.equals("HEALTHY");
    }
}
