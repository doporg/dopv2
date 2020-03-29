package com.clsaa.dop.server.api.dao.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public String getKongRouteId() {
        return kongRouteId;
    }

    public void setKongRouteId(String kongRouteId) {
        this.kongRouteId = kongRouteId;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getRequestPath() {
        return requestPath;
    }

    public void setRequestPath(String requestPath) {
        this.requestPath = requestPath;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }
}
