package com.clsaa.dop.server.api.module.kong.logModule.responseLog;


import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseHeadersLog {
    @JsonProperty(value = "Content-Length")
    private String contentLength;

    private String via;

    @JsonProperty(value = "Connection")
    private String connection;

    @JsonProperty(value = "access-control-allow-credentials")
    private String accessControlAllowCredentials;

    @JsonProperty(value = "Content-Type")
    private String contentType;

    private String server;

    @JsonProperty(value = "access-control-allow-origin")
    private String accessControlAllowOrigin;

    public ResponseHeadersLog() {
    }

    public String getContentLength() {
        return contentLength;
    }

    public String getVia() {
        return via;
    }

    public String getConnection() {
        return connection;
    }

    public String getAccessControlAllowCredentials() {
        return accessControlAllowCredentials;
    }

    public String getContentType() {
        return contentType;
    }

    public String getServer() {
        return server;
    }

    public String getAccessControlAllowOrigin() {
        return accessControlAllowOrigin;
    }
}
