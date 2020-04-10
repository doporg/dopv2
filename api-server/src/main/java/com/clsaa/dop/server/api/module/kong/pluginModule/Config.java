package com.clsaa.dop.server.api.module.kong.pluginModule;

public class Config {
    private String policy;
    private int second;
    private int minute;
    private int hour;
    private int day;
    private int mouth;
    private int year;
    private int redis_timeout;
    private int redis_database;
    private int redis_port;
    private String redis_password;
    private String limit_by;
    private String redis_host;
    private boolean hide_client_headers;
    private boolean fault_tolerant;

    public Config() {
    }

    public Config(String policy, int second, int minute, int hour, int day, int mouth, int year, int redis_timeout, int redis_database, int redis_port, String redis_password, String limit_by, String redis_host, boolean hide_client_headers, boolean fault_tolerant) {
        this.policy = policy;
        this.second = second;
        this.minute = minute;
        this.hour = hour;
        this.day = day;
        this.mouth = mouth;
        this.year = year;
        this.redis_timeout = redis_timeout;
        this.redis_database = redis_database;
        this.redis_port = redis_port;
        this.redis_password = redis_password;
        this.limit_by = limit_by;
        this.redis_host = redis_host;
        this.hide_client_headers = hide_client_headers;
        this.fault_tolerant = fault_tolerant;
    }

    public String getPolicy() {
        return policy;
    }

    public int getSecond() {
        return second;
    }

    public int getMinute() {
        return minute;
    }

    public int getHour() {
        return hour;
    }

    public int getDay() {
        return day;
    }

    public int getMouth() {
        return mouth;
    }

    public int getYear() {
        return year;
    }

    public int getRedis_timeout() {
        return redis_timeout;
    }

    public int getRedis_database() {
        return redis_database;
    }

    public int getRedis_port() {
        return redis_port;
    }

    public String getRedis_password() {
        return redis_password;
    }

    public String getLimit_by() {
        return limit_by;
    }

    public String getRedis_host() {
        return redis_host;
    }

    public boolean isHide_client_headers() {
        return hide_client_headers;
    }

    public boolean isFault_tolerant() {
        return fault_tolerant;
    }
}
