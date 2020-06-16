package com.clsaa.dop.server.api.module.vo.response.monitor;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@Data
@NoArgsConstructor
public class ApiRequestLogList {
    private int totalCount;
    private int current;
    private List<ApiRequestLogDetail> apiRequestLogs;

    public ApiRequestLogList(int totalCount, int current) {
        this.current = current;
        this.totalCount = totalCount;
        apiRequestLogs = new LinkedList<>();
    }

    public void  addApiRequestLog(ApiRequestLogDetail apiRequestLog){
        apiRequestLogs.add(apiRequestLog);
    }
}
