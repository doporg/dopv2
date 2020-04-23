package com.clsaa.dop.server.api.module.po;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Service {
    @Id
    private String id;

    @Column(nullable = false, columnDefinition = "varchar(255) character set utf8")
    private String name;

    @Column(nullable = false, columnDefinition = "varchar(255) character set utf8")
    private String description;

    @Column(nullable = false)
    private String rateLimitingPluginId;

    @Column(nullable = false)
    private String proxyCachePluginId;

    @Column(nullable = false)
    private Long timeout;

    @Column(nullable = false)
    private boolean caching;

    @Column(nullable = false)
    private Long cachingTime;

    @Column(nullable = false)
    private boolean fuse;

    @Column(nullable = false)
    private Long fuseDetectionRing;

    @Column(nullable = false)
    private Long criticalFusingFailureRate;

    @Column(nullable = false)
    private Long fuseDuration;

    @Column(nullable = false)
    private Long replyDetectionRingSize;

    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=false)
    @JoinColumn(name = "serviceRoute_id",referencedColumnName = "id")
    private ServiceRoute serviceRoute;

    @OneToMany(mappedBy  = "service",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<CurrentLimitPolicy> currentLimitPolicies;


    public Service(String id, String name, String description, Long timeout,
                   boolean fuse, Long fuseDetectionRing, Long criticalFusingFailureRate, Long fuseDuration, Long replyDetectionRingSize, ServiceRoute serviceRoute) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.timeout = timeout;
        this.caching = false;
        this.cachingTime = 300L;
        this.fuse = fuse;
        this.fuseDetectionRing = fuseDetectionRing;
        this.criticalFusingFailureRate = criticalFusingFailureRate;
        this.fuseDuration = fuseDuration;
        this.replyDetectionRingSize = replyDetectionRingSize;
        this.serviceRoute = serviceRoute;
        this.rateLimitingPluginId = "";
        this.proxyCachePluginId = "";
    }
}
