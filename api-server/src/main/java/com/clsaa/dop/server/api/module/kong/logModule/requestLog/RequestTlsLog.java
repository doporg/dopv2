package com.clsaa.dop.server.api.module.kong.logModule.requestLog;


public class RequestTlsLog {
    private String version;
    private String cipher;
    private String supported_client_ciphers;
    private String client_verify;

    public RequestTlsLog() {
    }

    public RequestTlsLog(String version, String cipher, String supported_client_ciphers, String client_verify) {
        this.version = version;
        this.cipher = cipher;
        this.supported_client_ciphers = supported_client_ciphers;
        this.client_verify = client_verify;
    }

    public String getVersion() {
        return version;
    }

    public String getCipher() {
        return cipher;
    }

    public String getSupported_client_ciphers() {
        return supported_client_ciphers;
    }

    public String getClient_verify() {
        return client_verify;
    }
}


