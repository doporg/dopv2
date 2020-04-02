package com.clsaa.dop.server.link.model;

import com.clsaa.dop.server.link.enums.Kind;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Span {

    /**
     * id: "352bff9a74ca9ad2"
     * traceId: "5af7183fb1d4cf5f"
     * parentId: "6b221d5bc9e6496c"
     * name: "get /api"
     * timestamp: 1556604172355737
     * duration: 1431
     * kind: "SERVER"
     * localEndpoint:
     *   serviceName: "backend"
     *   ipv4: "192.168.99.1"
     *   port: 3306
     * remoteEndpoint:
     *   ipv4: "172.19.0.2"
     *   port: 58648
     * tags:
     *   http.method: "GET"
     *   http.path: "/api"
     */

    private String traceId;//necessary

    private String name;

    private String parentId;

    private String id;//spanId necessary

    private Kind kind;

    private long timestamp;

    private long duration; // zero means null, saving 2 object references

    private boolean debug;

    private boolean shared;

    private Endpoint localEndpoint;

    private Endpoint remoteEndpoint;

    private List<Annotation> annotations;

    private Map<String, String> tags;
}
