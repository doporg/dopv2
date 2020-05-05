package com.clsaa.dop.server.api.module.kong.upstreamModule;

public class UpstreamHealth {
    private String next;
    private UpstreamHealthData data;
    private String node_id;

    public UpstreamHealth() {
    }

    public UpstreamHealth(String next, UpstreamHealthData data, String node_id) {
        this.next = next;
        this.data = data;
        this.node_id = node_id;
    }

    public String getNext() {
        return next;
    }

    public UpstreamHealthData getData() {
        return data;
    }

    public String getHealthData() {
        return data.getHealth();
    }

    public String getNode_id() {
        return node_id;
    }


}
