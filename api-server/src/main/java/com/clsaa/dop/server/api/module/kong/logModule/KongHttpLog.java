package com.clsaa.dop.server.api.module.kong.logModule;

import com.clsaa.dop.server.api.module.kong.logModule.requestLog.RequestLog;
import com.clsaa.dop.server.api.module.kong.logModule.responseLog.ResponseLog;

public class KongHttpLog {
    private RequestLog request;
    private String upstream_uri;
    private ResponseLog response;
    private TryLog[] tries;
    private AuthenticatedEntityLog authenticated_entity;
    private RouteLog route;
    private ServiceLog service;
    private WorkspaceLog[] workspaces;
    private ConsumerLog consumer;
    private Latencies latencies;
    private String client_ip;
    private long started_at;


    public KongHttpLog() {
    }

    public KongHttpLog(RequestLog request, String upstream_uri, ResponseLog response, TryLog[] tries, AuthenticatedEntityLog authenticated_entity, RouteLog route, ServiceLog service, WorkspaceLog[] workspaces, ConsumerLog consumer, Latencies latencies, String client_ip, long started_at) {
        this.request = request;
        this.upstream_uri = upstream_uri;
        this.response = response;
        this.tries = tries;
        this.authenticated_entity = authenticated_entity;
        this.route = route;
        this.service = service;
        this.workspaces = workspaces;
        this.consumer = consumer;
        this.latencies = latencies;
        this.client_ip = client_ip;
        this.started_at = started_at;
    }

    public RequestLog getRequest() {
        return request;
    }

    public String getUpstream_uri() {
        return upstream_uri;
    }

    public ResponseLog getResponse() {
        return response;
    }

    public TryLog[] getTries() {
        return tries;
    }

    public AuthenticatedEntityLog getAuthenticated_entity() {
        return authenticated_entity;
    }

    public RouteLog getRoute() {
        return route;
    }

    public ServiceLog getService() {
        return service;
    }

    public WorkspaceLog[] getWorkspaces() {
        return workspaces;
    }

    public ConsumerLog getConsumer() {
        return consumer;
    }

    public Latencies getLatencies() {
        return latencies;
    }

    public String getClient_ip() {
        return client_ip;
    }

    public long getStarted_at() {
        return started_at;
    }
}
