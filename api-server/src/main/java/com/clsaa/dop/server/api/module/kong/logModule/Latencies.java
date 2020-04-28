package com.clsaa.dop.server.api.module.kong.logModule;

public class Latencies {
    private int proxy;
    private int kong;
    private int request;

    public Latencies() {
    }

    public Latencies(int proxy, int kong, int request) {
        this.proxy = proxy;
        this.kong = kong;
        this.request = request;
    }

    public int getProxy() {
        return proxy;
    }

    public int getKong() {
        return kong;
    }

    public int getRequest() {
        return request;
    }
}
