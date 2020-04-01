package com.clsaa.dop.server.api.dao.entity;


import javax.persistence.*;

@Entity
public class Target {
    @Id
    private String id;

    @Column(nullable = false)
    private String host;

    @Column(nullable = false)
    private int port;

    @Column(nullable =false)
    private int weights;

    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=false)
    @JoinColumn(name = "Upstream_id",referencedColumnName = "id")
    private Upstream upstream;

    public Target(String id, String host, int port, int weights, Upstream upstream) {
        this.id = id;
        this.host = host;
        this.port = port;
        this.weights = weights;
        this.upstream = upstream;
    }

    public Target() {
    }

    public String getId() {
        return id;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public int getWeights() {
        return weights;
    }

    public Upstream getUpstream() {
        return upstream;
    }
}
