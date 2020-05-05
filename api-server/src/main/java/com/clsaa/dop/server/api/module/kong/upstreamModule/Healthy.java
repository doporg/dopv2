package com.clsaa.dop.server.api.module.kong.upstreamModule;

public class Healthy {
    private Long[] http_statuses;
    private Long interval;
    private Long successes;


    public Healthy(Long[] http_statuses, Long interval, Long successes) {
        this.http_statuses = http_statuses;
        this.interval = interval;
        this.successes = successes;
    }

    public Healthy() {
    }

    public Long[] getHttp_statuses() {
        return http_statuses;
    }

    public void setHttp_statuses(Long[] http_statuses) {
        this.http_statuses = http_statuses;
    }

    public Long getInterval() {
        return interval;
    }

    public void setInterval(Long interval) {
        this.interval = interval;
    }

    public Long getSuccesses() {
        return successes;
    }

    public void setSuccesses(Long successes) {
        this.successes = successes;
    }
}
