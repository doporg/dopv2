package com.clsaa.dop.server.api.module.po;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Data
public class Route {

    @Id
    @GenericGenerator(name = "jpa-uuid", strategy = "uuid")
    @GeneratedValue(generator = "jpa-uuid")
    private String id;

    @Column(nullable = false,unique = true)
    private String kongRouteId;

    @Column(nullable = false)
    private boolean online;

    @Column(nullable = false)
    private String requestMethod;

    @Column(nullable = false, columnDefinition = "varchar(255) character set utf8")
    private String requestPath;

    @OneToOne()
    @JoinColumn(name = "service_id",referencedColumnName = "id")
    private Service service;


    public Route() {
    }

    public Route(boolean online, String requestMethod, String requestPath, Service service) {
        this.online = online;
        this.requestMethod = requestMethod;
        this.requestPath = requestPath;
        this.service = service;
        kongRouteId = "";
    }
}
