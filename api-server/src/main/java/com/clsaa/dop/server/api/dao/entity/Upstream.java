package com.clsaa.dop.server.api.dao.entity;


import javax.persistence.*;
import java.util.List;

@Entity
public class Upstream {
    @Id
    private String id;

    @Column(unique = true,nullable = false)
    private String name;

    @Column(nullable = false)
    private String algorithm;

    @Column(nullable = false)
    private String hash_on;

    @Column(nullable = false)
    private String header;

    @OneToMany(mappedBy = "upstream",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    private List<Target> targets;

    public Upstream() {
    }

    public Upstream(String id) {
        this.id = id;
        this.algorithm = "round-robin";
    }


    public Upstream(String id, String name, String algorithm, String hash_on, String header) {
        this.id = id;
        this.name = name;
        this.algorithm = algorithm;
        this.hash_on = hash_on;
        this.header = header;
    }

    public String getId() {
        return id;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public String getHash_on() {
        return hash_on;
    }

    public String getHeader() {
        return header;
    }

    public String getName() {
        return name;
    }

    public List<Target> getTargets() {
        return targets;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public void setHash_on(String hash_on) {
        this.hash_on = hash_on;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void setTargets(List<Target> targets) {
        this.targets = targets;
    }
}
