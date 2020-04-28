package com.clsaa.dop.server.api.module.po;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
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
    private String type;

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


    public ServiceRoute(String name,String description,String type, String host,String path, Upstream upstream) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.host = host;
        this.port = 80;
        this.path = path;
        this.upstream = upstream;
    }
}
