package com.clsaa.dop.server.api.module.kong.logModule;

public class ConsumerLog {
    private String username;
    private long created_at;
    private String id;

    public ConsumerLog() {
    }

    public ConsumerLog(String username, long created_at, String id) {
        this.username = username;
        this.created_at = created_at;
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public long getCreated_at() {
        return created_at;
    }

    public String getId() {
        return id;
    }
}
