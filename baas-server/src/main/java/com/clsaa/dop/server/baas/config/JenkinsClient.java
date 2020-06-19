package com.clsaa.dop.server.baas.config;

/**
 * 连接jekins客户端
 *
 * @author Monhey
 */
public class JenkinsClient {
    private static String uri = "http://jenkins.dop.clsaa.com:8088";
    private static String username = "dop";
    private static String password = "Dop123..";

    public JenkinsClient() {
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

