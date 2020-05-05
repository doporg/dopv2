package com.clsaa.dop.server.api.module.kong.pluginModule.currentLimiting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrentLimitingPlugin {
    private long created_at;

    private CurrentLimitConfig config;

    private String id;

    private Object service;

    private boolean enabled;

    private String[] protocols;

    private String name;

    private Object consumer;

    private Object route;

    private String[] tags;
}
