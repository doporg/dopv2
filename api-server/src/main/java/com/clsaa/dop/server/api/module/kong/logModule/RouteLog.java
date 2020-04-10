package com.clsaa.dop.server.api.module.kong.logModule;

import com.clsaa.dop.server.api.module.kong.serviceModule.KongServiceId;

public class RouteLog {
    private Long created_at;

    private String[] hosts;

    private String id;

    private String[] methods;

    private String[] paths;

    private boolean preserve_host;

    private String[] protocols;

    private Long regex_priority;

    private KongServiceId service;

    private boolean strip_path;

    private Long updated_at;

    public RouteLog() {
    }

    public RouteLog(Long created_at, String[] hosts, String id, String[] methods, String[] paths, boolean preserve_host, String[] protocols, Long regex_priority, KongServiceId service, boolean strip_path, Long updated_at) {
        this.created_at = created_at;
        this.hosts = hosts;
        this.id = id;
        this.methods = methods;
        this.paths = paths;
        this.preserve_host = preserve_host;
        this.protocols = protocols;
        this.regex_priority = regex_priority;
        this.service = service;
        this.strip_path = strip_path;
        this.updated_at = updated_at;
    }

    public Long getCreated_at() {
        return created_at;
    }

    public String[] getHosts() {
        return hosts;
    }

    public String getId() {
        return id;
    }

    public String[] getMethods() {
        return methods;
    }

    public String[] getPaths() {
        return paths;
    }

    public boolean isPreserve_host() {
        return preserve_host;
    }

    public String[] getProtocols() {
        return protocols;
    }

    public Long getRegex_priority() {
        return regex_priority;
    }

    public KongServiceId getService() {
        return service;
    }

    public boolean isStrip_path() {
        return strip_path;
    }

    public Long getUpdated_at() {
        return updated_at;
    }
}
