package com.clsaa.dop.server.api.module.kong.pluginModule.proxyCache;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProxyCacheConfig {
    private String[] content_type;
    private Memory memory;
    private String strategy;
    private String vary_headers;
    private int cache_ttl;
    private String[] response_code;
    private int storage_ttl;
    private String vary_query_params;
    private boolean cache_control;
    private String[] request_method;
}
