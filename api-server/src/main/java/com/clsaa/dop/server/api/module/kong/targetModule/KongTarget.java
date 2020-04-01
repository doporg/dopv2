package com.clsaa.dop.server.api.module.kong.targetModule;

public class KongTarget {
    private Long created_at;

    private String id;

    private ID upstream;

    private String target;

    private Long weight;

    public KongTarget() {
    }

    public KongTarget(Long created_at, String id, ID upstream, String target, Long weight) {
        this.created_at = created_at;
        this.id = id;
        this.upstream = upstream;
        this.target = target;
        this.weight = weight;
    }

    public Long getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Long created_at) {
        this.created_at = created_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ID getUpstream() {
        return upstream;
    }

    public void setUpstream(ID upstream) {
        this.upstream = upstream;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Long getWeight() {
        return weight;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }
}
