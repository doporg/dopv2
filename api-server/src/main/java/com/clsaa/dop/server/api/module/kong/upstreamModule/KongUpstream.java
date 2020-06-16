package com.clsaa.dop.server.api.module.kong.upstreamModule;

public class KongUpstream {
    private String id;

    private Long created_at;

    private String name;

    private String algorithm;

    private String hash_on;

    private String hash_fallback;

    private String hash_on_cookie_path;

    private Long slots;

    private Healthchecks healthchecks;

    private String[] tags;

    private String host_header;

    public KongUpstream() {
    }

    public KongUpstream(String id, Long created_at, String name, String algorithm, String hash_on, String hash_fallback, String hash_on_cookie_path, Long slots, Healthchecks healthchecks, String[] tags, String host_header) {
        this.id = id;
        this.created_at = created_at;
        this.name = name;
        this.algorithm = algorithm;
        this.hash_on = hash_on;
        this.hash_fallback = hash_fallback;
        this.hash_on_cookie_path = hash_on_cookie_path;
        this.slots = slots;
        this.healthchecks = healthchecks;
        this.tags = tags;
        this.host_header = host_header;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Long created_at) {
        this.created_at = created_at;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getHash_on() {
        return hash_on;
    }

    public void setHash_on(String hash_on) {
        this.hash_on = hash_on;
    }

    public String getHash_fallback() {
        return hash_fallback;
    }

    public void setHash_fallback(String hash_fallback) {
        this.hash_fallback = hash_fallback;
    }

    public String getHash_on_cookie_path() {
        return hash_on_cookie_path;
    }

    public void setHash_on_cookie_path(String hash_on_cookie_path) {
        this.hash_on_cookie_path = hash_on_cookie_path;
    }

    public Long getSlots() {
        return slots;
    }

    public void setSlots(Long slots) {
        this.slots = slots;
    }

    public Healthchecks getHealthchecks() {
        return healthchecks;
    }

    public void setHealthchecks(Healthchecks healthchecks) {
        this.healthchecks = healthchecks;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String getHost_header() {
        return host_header;
    }

    public void setHost_header(String host_header) {
        this.host_header = host_header;
    }
}
