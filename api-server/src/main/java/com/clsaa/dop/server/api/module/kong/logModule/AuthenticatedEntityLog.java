package com.clsaa.dop.server.api.module.kong.logModule;

public class AuthenticatedEntityLog {
    private String consumer_id;
    private String id;

    public AuthenticatedEntityLog() {
    }

    public AuthenticatedEntityLog(String consumer_id, String id) {
        this.consumer_id = consumer_id;
        this.id = id;
    }

    public String getConsumer_id() {
        return consumer_id;
    }

    public String getId() {
        return id;
    }
}
