package com.clsaa.dop.server.api.service;

import com.clsaa.dop.server.api.module.kong.logModule.KongHttpLog;
import com.clsaa.dop.server.api.module.vo.response.ResponseResult;
import com.clsaa.dop.server.api.module.vo.response.monitor.ApiRequestLog;
import com.clsaa.dop.server.api.module.vo.response.monitor.ApiRequestLogDetail;
import com.clsaa.dop.server.api.module.vo.response.monitor.TrafficStatistics;

import java.util.List;

public interface MonitorService {
    ResponseResult<TrafficStatistics> getTrafficStatistics();

    ResponseResult receiveRequestLog(KongHttpLog kongHttpLog);

    ResponseResult<List<ApiRequestLog>> getRequestLog(String beginTime, String endTime, int statusCode);

    ResponseResult<List<ApiRequestLog>> getRequestLog();

    ResponseResult<ApiRequestLogDetail> getRequestLogDetail(String logId);
}
