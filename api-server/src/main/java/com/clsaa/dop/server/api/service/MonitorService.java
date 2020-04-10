package com.clsaa.dop.server.api.service;

import com.clsaa.dop.server.api.module.kong.logModule.KongHttpLog;
import com.clsaa.dop.server.api.module.response.ResponseResult;
import com.clsaa.dop.server.api.module.response.monitor.ApiRequestLog;
import com.clsaa.dop.server.api.module.response.monitor.ApiRequestLogDetail;

import java.util.List;

public interface MonitorService {
    ResponseResult receiveRequestLog(KongHttpLog kongHttpLog);

    ResponseResult<List<ApiRequestLog>> getRequestLog(String beginTime, String endTime, int statusCode);

    ResponseResult<List<ApiRequestLog>> getRequestLog();

    ResponseResult<ApiRequestLogDetail> getRequestLogDetail(String logId);
}
