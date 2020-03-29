package com.clsaa.dop.server.api.dao.entity;

import javax.persistence.*;

@Entity
public class Upstream {
    @Id
    private String id;

    @Column(nullable = false)
    private String host;


    public Upstream(String id, String host) {
        this.id = id;
        this.host = host;
    }

    public Upstream() {
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
