package com.clsaa.dop.server.api.module.kong.serviceModule;

public class KongService {
    private String host;
    private String created_at;
    private Long  connect_timeout;
    private String id;
    private String protocol;
    private String name;
    private Long read_timeout;
    private Long port;
    private String path;
    private String updated_at;
    private Long retries;
    private Long write_timeout;
    private String tags;
    private String client_certificate;

    public KongService() {
    }

    public KongService(String host, String created_at, Long connect_timeout, String id, String protocol, String name,
                       Long read_timeout, Long port, String path, String updated_at, Long retries, Long write_timeout, String tags, String client_certificate) {
        this.host = host;
        this.created_at = created_at;
        this.connect_timeout = connect_timeout;
        this.id = id;
        this.protocol = protocol;
        this.name = name;
        this.read_timeout = read_timeout;
        this.port = port;
        this.path = path;
        this.updated_at = updated_at;
        this.retries = retries;
        this.write_timeout = write_timeout;
        this.tags = tags;
        this.client_certificate = client_certificate;
    }

    public String getHost() {
        return host;
    }

    public String getCreated_at() {
        return created_at;
    }

    public Long getConnect_timeout() {
        return connect_timeout;
    }

    public String getId() {
        return id;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getName() {
        return name;
    }

    public Long getRead_timeout() {
        return read_timeout;
    }

    public Long getPort() {
        return port;
    }

    public String getPath() {
        return path;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public Long getRetries() {
        return retries;
    }

    public Long getWrite_timeout() {
        return write_timeout;
    }

    public String getTags() {
        return tags;
    }

    public String getClient_certificate() {
        return client_certificate;
    }
}
