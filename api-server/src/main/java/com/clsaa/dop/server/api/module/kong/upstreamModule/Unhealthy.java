package com.clsaa.dop.server.api.module.kong.upstreamModule;

public class Unhealthy {
    private Long[] http_statuses;

    private Long tcp_failures;

    private Long timeouts;

    private Long http_failures;

    private Long interval;

    public Unhealthy() {
    }

    public Unhealthy(Long[] http_statuses, Long tcp_failures, Long timeouts, Long http_failures, Long interval) {
        this.http_statuses = http_statuses;
        this.tcp_failures = tcp_failures;
        this.timeouts = timeouts;
        this.http_failures = http_failures;
        this.interval = interval;
    }

    public Long[] getHttp_statuses() {
        return http_statuses;
    }

    public void setHttp_statuses(Long[] http_statuses) {
        this.http_statuses = http_statuses;
    }

    public Long getTcp_failures() {
        return tcp_failures;
    }

    public void setTcp_failures(Long tcp_failures) {
        this.tcp_failures = tcp_failures;
    }

    public Long getTimeouts() {
        return timeouts;
    }

    public void setTimeouts(Long timeouts) {
        this.timeouts = timeouts;
    }

    public Long getHttp_failures() {
        return http_failures;
    }

    public void setHttp_failures(Long http_failures) {
        this.http_failures = http_failures;
    }

    public Long getInterval() {
        return interval;
    }

    public void setInterval(Long interval) {
        this.interval = interval;
    }
}
