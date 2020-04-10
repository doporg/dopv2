package com.clsaa.dop.server.api.dao.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class CurrentLimitPolicy {
    @Id
    @GenericGenerator(name = "jpa-uuid", strategy = "uuid")
    @GeneratedValue(generator = "jpa-uuid")
    private String id;

    @Column(nullable = false,unique = true)
    private String name;

    @Column(nullable = false)
    private String circle;

    @Column(nullable = false)
    private int time;

    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=false)
    @JoinColumn(name = "service_id")
    private Service service;

    public CurrentLimitPolicy() {
    }

    public CurrentLimitPolicy(String name, String circle, int time, Service service) {
        this.name = name;
        this.circle = circle;
        this.time = time;
        this.service = service;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCircle(String circle) {
        this.circle = circle;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCircle() {
        return circle;
    }

    public int getTime() {
        return time;
    }

    public Service getService() {
        return service;
    }
}
