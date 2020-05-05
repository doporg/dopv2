package com.clsaa.dop.server.api.module.kong.pluginModule.proxyCache;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProxyCachePlugin {
    private long created_at;

    private ProxyCacheConfig config;

    private String id;

    private Object service;

    private boolean enabled;

    private String[] protocols;

    private String name;

    private Object consumer;

    private Object route;

    private String[] tags;
}
