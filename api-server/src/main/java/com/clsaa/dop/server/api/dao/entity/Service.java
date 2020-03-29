package com.clsaa.dop.server.api.dao.entity;

import javax.persistence.*;

@Entity
public class Service {
    @Id
    private String id;

    @Column(nullable = false, columnDefinition = "varchar(255) character set utf8")
    private String name;

    @Column(nullable = false, columnDefinition = "varchar(255) character set utf8")
    private String description;

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

    @OneToOne()
    @JoinColumn(name = "upstream_id",referencedColumnName = "id")
    private Upstream upstream;

    public Service() {
    }

    public Service(String id, String name, String description, Long timeout, boolean caching, Long cachingTime,
                   boolean fuse, Long fuseDetectionRing, Long criticalFusingFailureRate, Long fuseDuration, Long replyDetectionRingSize, Upstream upstream) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.timeout = timeout;
        this.caching = caching;
        this.cachingTime = cachingTime;
        this.fuse = fuse;
        this.fuseDetectionRing = fuseDetectionRing;
        this.criticalFusingFailureRate = criticalFusingFailureRate;
        this.fuseDuration = fuseDuration;
        this.replyDetectionRingSize = replyDetectionRingSize;
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

    public Long getTimeout() {
        return timeout;
    }

    public void setTimeout(Long timeout) {
        this.timeout = timeout;
    }

    public boolean isCaching() {
        return caching;
    }

    public void setCaching(boolean caching) {
        this.caching = caching;
    }

    public Long getCachingTime() {
        return cachingTime;
    }

    public void setCachingTime(Long cachingTime) {
        this.cachingTime = cachingTime;
    }

    public boolean isFuse() {
        return fuse;
    }

    public void setFuse(boolean fuse) {
        this.fuse = fuse;
    }

    public Long getFuseDetectionRing() {
        return fuseDetectionRing;
    }

    public void setFuseDetectionRing(Long fuseDetectionRing) {
        this.fuseDetectionRing = fuseDetectionRing;
    }

    public Long getCriticalFusingFailureRate() {
        return criticalFusingFailureRate;
    }

    public void setCriticalFusingFailureRate(Long criticalFusingFailureRate) {
        this.criticalFusingFailureRate = criticalFusingFailureRate;
    }

    public Long getFuseDuration() {
        return fuseDuration;
    }

    public void setFuseDuration(Long fuseDuration) {
        this.fuseDuration = fuseDuration;
    }

    public Long getReplyDetectionRingSize() {
        return replyDetectionRingSize;
    }

    public void setReplyDetectionRingSize(Long replyDetectionRingSize) {
        this.replyDetectionRingSize = replyDetectionRingSize;
    }

    public Upstream getUpstream() {
        return upstream;
    }

    public void setUpstream(Upstream upstream) {
        this.upstream = upstream;
    }
}
