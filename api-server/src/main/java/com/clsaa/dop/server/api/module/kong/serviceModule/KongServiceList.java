package com.clsaa.dop.server.api.module.kong.serviceModule;

public class KongServiceList {
    private String next;
    private KongService[] data;

    public KongServiceList() {
    }

    public String getNext() {
        return next;
    }

    public KongService[] getData() {
        return data;
    }
}
