package com.clsaa.dop.server.api.module.kong.logModule.requestLog;


import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestHeadersLog {
    private String accept;
    private String host;
    @JsonProperty(value = "user-agent")
    private String userAgent;

    public RequestHeadersLog() {
    }

    public RequestHeadersLog(String accept, String host, String userAgent) {
        this.accept = accept;
        this.host = host;
        this.userAgent = userAgent;
    }

    public String getAccept() {
        return accept;
    }

    public String getHost() {
        return host;
    }

    public String getUserAgent() {
        return userAgent;
    }
}
