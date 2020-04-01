package com.clsaa.dop.server.api.dao.entity;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
public class ServiceRoute {
    @Id
    @GenericGenerator(name = "jpa-uuid", strategy = "uuid")
    @GeneratedValue(generator = "jpa-uuid")
    private String id;

    @Column(unique = true,nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String host;

    @Column(nullable = false)
    private long port;

    @Column(nullable = false)
    private String path;

    @OneToOne()
    @JoinColumn(name = "upstream_id",referencedColumnName = "id")
    private Upstream upstream;

    @OneToMany(mappedBy = "serviceRoute",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    private List<Service> services;

    public ServiceRoute() {
    }

    public ServiceRoute(String name, String description, String host, long port, String path) {
        this.name = name;
        this.description = description;
        this.host = host;
        this.port = port;
        this.path = path;
    }


    public ServiceRoute(String name,String description, String host,String path, Upstream upstream) {
        this.name = name;
        this.description = description;
        this.host = host;
        this.port = 80;
        this.path = path;
        this.upstream = upstream;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public long getPort() {
        return port;
    }

    public void setPort(long port) {
        this.port = port;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Upstream getUpstream() {
        return upstream;
    }

    public void setUpstream(Upstream upstream) {
        this.upstream = upstream;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public String getType(){
        if (upstream==null){
            return "ServiceDiscoveryPolicy";
        }else {
            return "WeightingPolicy";
        }
    }
}
