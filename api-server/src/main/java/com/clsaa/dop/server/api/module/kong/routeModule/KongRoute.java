package com.clsaa.dop.server.api.module.kong.routeModule;

import com.clsaa.dop.server.api.module.kong.serviceModule.KongServiceId;

public class KongRoute {
    private String id;

    private String path_handling;

    private String[] paths;

    private String destinations;

    private String headers;

    private String[] protocols;

    private String[] methods;

    private String snis;

    private KongServiceId service;

    private String name;

    private boolean strip_path;

    private boolean preserve_host;

    private Long regex_priority;

    private Long updated_at;

    private String sources;

    private String[] hosts;

    private Long https_redirect_status_code;

    private String[] tags;

    private Long created_at;


    public KongRoute() {
    }

    public KongRoute(String id, String path_handling, String[] paths, String destinations, String headers, String[] protocols,
                     String[] methods, String snis, KongServiceId service, String name, boolean strip_path, boolean preserve_host, Long regex_priority, Long updated_at, String sources, String[] hosts, Long https_redirect_status_code, String[] tags, Long created_at) {
        this.id = id;
        this.path_handling = path_handling;
        this.paths = paths;
        this.destinations = destinations;
        this.headers = headers;
        this.protocols = protocols;
        this.methods = methods;
        this.snis = snis;
        this.service = service;
        this.name = name;
        this.strip_path = strip_path;
        this.preserve_host = preserve_host;
        this.regex_priority = regex_priority;
        this.updated_at = updated_at;
        this.sources = sources;
        this.hosts = hosts;
        this.https_redirect_status_code = https_redirect_status_code;
        this.tags = tags;
        this.created_at = created_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPath_handling() {
        return path_handling;
    }

    public void setPath_handling(String path_handling) {
        this.path_handling = path_handling;
    }

    public String[] getPaths() {
        return paths;
    }

    public void setPaths(String[] paths) {
        this.paths = paths;
    }

    public String getDestinations() {
        return destinations;
    }

    public void setDestinations(String destinations) {
        this.destinations = destinations;
    }

    public String getHeaders() {
        return headers;
    }

    public void setHeaders(String headers) {
        this.headers = headers;
    }

    public String[] getProtocols() {
        return protocols;
    }

    public void setProtocols(String[] protocols) {
        this.protocols = protocols;
    }

    public String[] getMethods() {
        return methods;
    }

    public void setMethods(String[] methods) {
        this.methods = methods;
    }

    public String getSnis() {
        return snis;
    }

    public void setSnis(String snis) {
        this.snis = snis;
    }

    public KongServiceId getService() {
        return service;
    }

    public void setService(KongServiceId service) {
        this.service = service;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isStrip_path() {
        return strip_path;
    }

    public void setStrip_path(boolean strip_path) {
        this.strip_path = strip_path;
    }

    public boolean isPreserve_host() {
        return preserve_host;
    }

    public void setPreserve_host(boolean preserve_host) {
        this.preserve_host = preserve_host;
    }

    public Long getRegex_priority() {
        return regex_priority;
    }

    public void setRegex_priority(Long regex_priority) {
        this.regex_priority = regex_priority;
    }

    public Long getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Long updated_at) {
        this.updated_at = updated_at;
    }

    public String getSources() {
        return sources;
    }

    public void setSources(String sources) {
        this.sources = sources;
    }

    public String[] getHosts() {
        return hosts;
    }

    public void setHosts(String[] hosts) {
        this.hosts = hosts;
    }

    public Long getHttps_redirect_status_code() {
        return https_redirect_status_code;
    }

    public void setHttps_redirect_status_code(Long https_redirect_status_code) {
        this.https_redirect_status_code = https_redirect_status_code;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public Long getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Long created_at) {
        this.created_at = created_at;
    }
}
