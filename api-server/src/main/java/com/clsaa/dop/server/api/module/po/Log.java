package com.clsaa.dop.server.api.module.po;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
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
    private int requestSize;

    @Column(nullable = false)
    private int responseStatus;

    @Column(nullable = false)
    private int responseSize;

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

    public Log(String requestMethod, String requestPath, int requestSize, int responseStatus, int responseSize,
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
}
