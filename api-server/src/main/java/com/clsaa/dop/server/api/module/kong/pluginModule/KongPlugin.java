package com.clsaa.dop.server.api.module.kong.pluginModule;

public class KongPlugin {
    private long created_at;

    private Config config;

    private String id;

    private Object service;

    private boolean enabled;

    private String[] protocols;

    private String name;

    private Object consumer;

    private Object route;

    private String[] tags;

    public KongPlugin() {
    }

    public KongPlugin(long created_at, Config config, String id, String service, boolean enabled, String[] protocols,
                      String name, String consumer, String route, String[] tags) {
        this.created_at = created_at;
        this.config = config;
        this.id = id;
        this.service = service;
        this.enabled = enabled;
        this.protocols = protocols;
        this.name = name;
        this.consumer = consumer;
        this.route = route;
        this.tags = tags;
    }

    public long getCreated_at() {
        return created_at;
    }


    public String getId() {
        return id;
    }


    public boolean isEnabled() {
        return enabled;
    }

    public String[] getProtocols() {
        return protocols;
    }

    public String getName() {
        return name;
    }


    public String[] getTags() {
        return tags;
    }

    public Config getConfig() {
        return config;
    }

    public Object getService() {
        return service;
    }

    public Object getConsumer() {
        return consumer;
    }

    public Object getRoute() {
        return route;
    }
}
