package com.clsaa.dop.server.api.service;

import com.clsaa.dop.server.api.module.kong.logModule.KongHttpLog;
import com.clsaa.dop.server.api.module.vo.response.ResponseResult;
import com.clsaa.dop.server.api.module.vo.response.monitor.ApiRequestLog;
import com.clsaa.dop.server.api.module.vo.response.monitor.ApiRequestLogDetail;
import com.clsaa.dop.server.api.module.vo.response.monitor.ApiRequestLogList;
import com.clsaa.dop.server.api.module.vo.response.monitor.TrafficStatistics;

import java.util.List;

public interface MonitorService {
    ResponseResult<TrafficStatistics> getTrafficStatistics(int time);

    ResponseResult receiveRequestLog(KongHttpLog kongHttpLog);

    ResponseResult<ApiRequestLogList> getRequestLog(String beginTime, String endTime, int statusCode,int pageNo, int pageSize);

    ResponseResult<ApiRequestLogList> getRequestLog(String apiId, int pageNo, int pageSize);

    ResponseResult<ApiRequestLogDetail> getRequestLogDetail(String logId);
}
