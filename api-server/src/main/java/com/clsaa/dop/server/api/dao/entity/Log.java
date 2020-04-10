package com.clsaa.dop.server.api.dao.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Log {
    @Id
    @GenericGenerator(name = "jpa-uuid", strategy = "uuid")
    @GeneratedValue(generator = "jpa-uuid")
    private String id;

    @Column(nullable = false)
    private String requestMethod;

    @Column(nullable = false)
    private String requestPath;

    @Column(nullable = false)
    private String requestSize;

    @Column(nullable = false)
    private int responseStatus;

    @Column(nullable = false)
    private String responseSize;

    @Column(nullable = false)
    private int proxyTimeout;

    @Column(nullable = false)
    private int responseTimeout;


    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date time;

    @Column(nullable = false)
    private String clientIP;

    @Column(nullable = false)
    private String serviceId;

    public Log() {
    }

    public Log(String requestMethod, String requestPath, String requestSize, int responseStatus, String responseSize,
               int proxyTimeout, int responseTimeout, Date time, String clientIP, String serviceId) {
        this.requestMethod = requestMethod;
        this.requestPath = requestPath;
        this.requestSize = requestSize;
        this.responseStatus = responseStatus;
        this.responseSize = responseSize;
        this.proxyTimeout = proxyTimeout;
        this.responseTimeout = responseTimeout;
        this.time = time;
        this.clientIP = clientIP;
        this.serviceId = serviceId;
    }

    public String getId() {
        return id;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public String getRequestPath() {
        return requestPath;
    }

    public String getRequestSize() {
        return requestSize;
    }

    public int getResponseStatus() {
        return responseStatus;
    }

    public int getProxyTimeout() {
        return proxyTimeout;
    }

    public int getResponseTimeout() {
        return responseTimeout;
    }

    public String getResponseSize() {
        return responseSize;
    }

    public Date getTime() {
        return time;
    }

    public String getClientIP() {
        return clientIP;
    }

    public String getServiceId() {
        return serviceId;
    }
}
