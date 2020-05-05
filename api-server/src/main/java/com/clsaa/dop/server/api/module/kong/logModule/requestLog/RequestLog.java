package com.clsaa.dop.server.api.module.kong.logModule.requestLog;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestLog {
    private String method;
    private String uri;
    private String url;
    private String size;
    @JsonProperty(value = "querystring")
    private Object queryString;
    private RequestHeadersLog headers;
    private RequestTlsLog tls;

    public RequestLog() {
    }

    public RequestLog(String method, String uri, String url, String size, Object queryString, RequestHeadersLog headers, RequestTlsLog tls) {
        this.method = method;
        this.uri = uri;
        this.url = url;
        this.size = size;
        this.queryString = queryString;
        this.headers = headers;
        this.tls = tls;
    }

    public String getMethod() {
        return method;
    }

    public String getUri() {
        return uri;
    }

    public String getUrl() {
        return url;
    }

    public String getSize() {
        return size;
    }

    public Object getQueryString() {
        return queryString;
    }

    public RequestHeadersLog getHeaders() {
        return headers;
    }

    public RequestTlsLog getTls() {
        return tls;
    }
}
