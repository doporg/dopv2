package com.clsaa.dop.server.api.serviceImpl;

import com.clsaa.dop.server.api.dao.entity.Log;
import com.clsaa.dop.server.api.dao.repository.LogRepository;
import com.clsaa.dop.server.api.module.kong.logModule.KongHttpLog;
import com.clsaa.dop.server.api.module.kong.logModule.Latencies;
import com.clsaa.dop.server.api.module.kong.logModule.ServiceLog;
import com.clsaa.dop.server.api.module.kong.logModule.requestLog.RequestLog;
import com.clsaa.dop.server.api.module.kong.logModule.responseLog.ResponseLog;
import com.clsaa.dop.server.api.module.response.ResponseResult;
import com.clsaa.dop.server.api.module.response.monitor.ApiRequestLog;
import com.clsaa.dop.server.api.module.response.monitor.ApiRequestLogDetail;
import com.clsaa.dop.server.api.service.MonitorService;

import com.clsaa.dop.server.api.util.UTCUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class MonitorServiceImpl implements MonitorService {
    private LogRepository logRepository;

    @Autowired
    public MonitorServiceImpl(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @Override
    public ResponseResult receiveRequestLog(KongHttpLog kongHttpLog) {
        RequestLog request = kongHttpLog.getRequest();
        ResponseLog response = kongHttpLog.getResponse();
        ServiceLog service = kongHttpLog.getService();
        Latencies latencies = kongHttpLog.getLatencies();

        Log log = new Log(request.getMethod(),request.getUri(),request.getSize(),response.getStatus(),response.getSize(),
                latencies.getProxy(),latencies.getRequest(),UTCUtils.utcMsToLocal(kongHttpLog.getStarted_at()),kongHttpLog.getClient_ip(),service.getId());

        logRepository.save(log);
        return new ResponseResult(0,"success");
    }

    @Override
    public ResponseResult<List<ApiRequestLog>> getRequestLog(String beginTime, String endTime, int statusCode) {
        Date beginDate = UTCUtils.stringToDate(beginTime);
        Date endDate = UTCUtils.stringToDate(endTime);
        if (beginDate!=null&&endDate!=null){
            List<Log> logs = logRepository.findByTimeAfterAndTimeBeforeAndResponseStatusOrderByTimeDesc(beginDate,endDate,statusCode);
            List<ApiRequestLog> apiRequestLogs = new LinkedList<>();
            for(Log log:logs){
                apiRequestLogs.add(new ApiRequestLog(log.getId(),log.getRequestPath(),log.getResponseStatus(),log.getProxyTimeout(),UTCUtils.dateToString(log.getTime())));
            }
            return new ResponseResult<>(0,"success",apiRequestLogs);
        }else {
            return new ResponseResult<>(1,"date format error");
        }
    }

    @Override
    public ResponseResult<List<ApiRequestLog>> getRequestLog() {
        List<Log> logs = logRepository.findTop10ByOrderByTimeDesc();
        List<ApiRequestLog> apiRequestLogs = new LinkedList<>();
        for(Log log:logs){
            apiRequestLogs.add(new ApiRequestLog(log.getId(),log.getRequestPath(),log.getResponseStatus(),log.getProxyTimeout(),UTCUtils.dateToString(log.getTime())));
        }
        return new ResponseResult<>(0,"success",apiRequestLogs);
    }

    @Override
    public ResponseResult<ApiRequestLogDetail> getRequestLogDetail(String logId) {
        Log log = logRepository.findLogById(logId);
        if (log!=null){
            ApiRequestLogDetail apiRequestLogDetail = new ApiRequestLogDetail(logId,log.getRequestMethod(),log.getRequestPath(),
                    log.getRequestSize(),log.getResponseStatus(),log.getProxyTimeout(),log.getResponseTimeout(),log.getResponseSize(),
                    UTCUtils.dateToString(log.getTime()),log.getClientIP(),log.getServiceId());
            return new ResponseResult<>(0,"success",apiRequestLogDetail);
        }else {
            return new ResponseResult<>(1,"log not found");
        }
    }
}
