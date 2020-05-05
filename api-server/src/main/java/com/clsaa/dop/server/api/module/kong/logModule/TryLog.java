package com.clsaa.dop.server.api.module.kong.logModule;

public class TryLog {
    private String state;
    private int code;
    private String ip;
    private int port;

    public TryLog() {
    }

    public TryLog(String state, int code, String ip, int port) {
        this.state = state;
        this.code = code;
        this.ip = ip;
        this.port = port;
    }

    public String getState() {
        return state;
    }

    public int getCode() {
        return code;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }
}
