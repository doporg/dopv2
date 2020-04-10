package com.clsaa.dop.server.api.module.kong.logModule;

public class WorkspaceLog {
    private String id;
    private String name;

    public WorkspaceLog() {
    }

    public WorkspaceLog(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
