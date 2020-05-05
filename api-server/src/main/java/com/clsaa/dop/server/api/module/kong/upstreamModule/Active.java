package com.clsaa.dop.server.api.module.kong.upstreamModule;

public class Active {
    private boolean https_verify_certificate;

    private Unhealthy unhealthy;

    private String http_path;

    private Long timeout;

    private Healthy healthy;

    private String https_sni;

    private Long concurrency;

    private String type;

    public Active() {
    }

    public Active(boolean https_verify_certificate, Unhealthy unhealthy, String http_path, Long timeout, Healthy healthy, String https_sni, Long concurrency, String type) {
        this.https_verify_certificate = https_verify_certificate;
        this.unhealthy = unhealthy;
        this.http_path = http_path;
        this.timeout = timeout;
        this.healthy = healthy;
        this.https_sni = https_sni;
        this.concurrency = concurrency;
        this.type = type;
    }

    public boolean isHttps_verify_certificate() {
        return https_verify_certificate;
    }

    public void setHttps_verify_certificate(boolean https_verify_certificate) {
        this.https_verify_certificate = https_verify_certificate;
    }

    public Unhealthy getUnhealthy() {
        return unhealthy;
    }

    public void setUnhealthy(Unhealthy unhealthy) {
        this.unhealthy = unhealthy;
    }

    public String getHttp_path() {
        return http_path;
    }

    public void setHttp_path(String http_path) {
        this.http_path = http_path;
    }

    public Long getTimeout() {
        return timeout;
    }

    public void setTimeout(Long timeout) {
        this.timeout = timeout;
    }

    public Healthy getHealthy() {
        return healthy;
    }

    public void setHealthy(Healthy healthy) {
        this.healthy = healthy;
    }

    public String getHttps_sni() {
        return https_sni;
    }

    public void setHttps_sni(String https_sni) {
        this.https_sni = https_sni;
    }

    public Long getConcurrency() {
        return concurrency;
    }

    public void setConcurrency(Long concurrency) {
        this.concurrency = concurrency;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
