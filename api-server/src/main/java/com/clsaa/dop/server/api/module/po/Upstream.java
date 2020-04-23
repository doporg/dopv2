package com.clsaa.dop.server.api.module.po;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Upstream {
    @Id
    private String id;

    @Column(unique = true,nullable = false)
    private String name;

    @Column(nullable = false)
    private boolean healthy;

    @Column(nullable = false)
    private String algorithm;

    @Column(nullable = false)
    private String hash_on;

    @Column(nullable = false)
    private String header;

    @OneToMany(mappedBy = "upstream",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    private List<Target> targets;

    public Upstream(String id,String name) {
        this.id = id;
        this.name = name;
        this.algorithm = "round-robin";
        this.hash_on = "";
        this.header = "";
        this.healthy = true;
    }


    public Upstream(String id, String name, String algorithm, String hash_on, String header) {
        this.id = id;
        this.name = name;
        this.algorithm = algorithm;
        this.hash_on = hash_on;
        this.header = header;
        this.healthy = true;
    }
}
