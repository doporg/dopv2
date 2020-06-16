package com.clsaa.dop.server.api.module.po;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
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

    public Target(String id, String host, Long port, Upstream upstream) {
        this.id = id;
        this.host = host;
        this.port = port.intValue();
        this.weights = 100;
        this.upstream = upstream;
    }

    public Target(String id, String host, int port, int weights, Upstream upstream) {
        this.id = id;
        this.host = host;
        this.port = port;
        this.weights = weights;
        this.upstream = upstream;
    }
}
