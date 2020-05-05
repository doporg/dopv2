package com.clsaa.dop.server.api.module.kong.logModule.responseLog;

import com.clsaa.dop.server.api.module.kong.logModule.requestLog.RequestHeadersLog;

public class ResponseLog {
    private int status;
    private String size;
    private ResponseHeadersLog headers;

    public ResponseLog() {
    }

    public ResponseLog(int status, String size, ResponseHeadersLog headers) {
        this.status = status;
        this.size = size;
        this.headers = headers;
    }

    public int getStatus() {
        return status;
    }

    public String getSize() {
        return size;
    }

    public ResponseHeadersLog getHeaders() {
        return headers;
    }
}
