package com.clsaa.dop.server.api.module.kong.serviceModule;

public class KongServiceId {
    private String id;

    public KongServiceId(String id) {
        this.id = id;
    }

    public KongServiceId() {
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
