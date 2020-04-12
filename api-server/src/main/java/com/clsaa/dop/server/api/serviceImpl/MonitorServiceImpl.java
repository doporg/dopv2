package com.clsaa.dop.server.api.serviceImpl;

import com.clsaa.dop.server.api.dao.ServiceRepository;
import com.clsaa.dop.server.api.module.po.Log;
import com.clsaa.dop.server.api.dao.LogRepository;
import com.clsaa.dop.server.api.module.kong.logModule.KongHttpLog;
import com.clsaa.dop.server.api.module.kong.logModule.Latencies;
import com.clsaa.dop.server.api.module.kong.logModule.ServiceLog;
import com.clsaa.dop.server.api.module.kong.logModule.requestLog.RequestLog;
import com.clsaa.dop.server.api.module.kong.logModule.responseLog.ResponseLog;
import com.clsaa.dop.server.api.module.po.Service;
import com.clsaa.dop.server.api.module.vo.response.ResponseResult;
import com.clsaa.dop.server.api.module.vo.response.monitor.ApiRequestLog;
import com.clsaa.dop.server.api.module.vo.response.monitor.ApiRequestLogDetail;
import com.clsaa.dop.server.api.module.vo.response.monitor.ApiSimpleInfo;
import com.clsaa.dop.server.api.module.vo.response.monitor.TrafficStatistics;
import com.clsaa.dop.server.api.service.MonitorService;

import com.clsaa.dop.server.api.util.UTCUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@org.springframework.stereotype.Service
public class MonitorServiceImpl implements MonitorService {
    private LogRepository logRepository;
    private ServiceRepository serviceRepository;

    @Autowired
    public MonitorServiceImpl(LogRepository logRepository,ServiceRepository serviceRepository) {
        this.logRepository = logRepository;
        this.serviceRepository = serviceRepository;
    }

    @Override
    public ResponseResult<TrafficStatistics> getTrafficStatistics() {
        Date currentTime = UTCUtils.timeBefore30m();
        TrafficStatistics trafficStatistics = new TrafficStatistics();
        int num = 5;

        //成功、失败请求数
        List<Integer> successStatus = getSuccessHttpStatus();
        List<Integer> failStatus = getFailHttpStatus();
        trafficStatistics.setSuccessfulRequests(logRepository.countLogByTimeAfterAndResponseStatusIn(currentTime,successStatus));
        trafficStatistics.setFailedRequests(logRepository.countLogByTimeAfterAndResponseStatusIn(currentTime,failStatus));

        //平均响应时间
        trafficStatistics.setResponseTime(getAverageResponseTime(currentTime));

        //调用前五的服务
        List<ApiSimpleInfo> highFrequencyApi = getFrequentService(currentTime,num);
        trafficStatistics.setHighFrequencyApi(highFrequencyApi);

        //响应时间前五服务
        List<ApiSimpleInfo> timeConsumingApi = getTimeConsumingService(currentTime,num);
        trafficStatistics.setTimeConsumingApi(timeConsumingApi);

        //失败次数前五服务
        List<ApiSimpleInfo> failApi =  getFailService(currentTime,num);
        trafficStatistics.setCallFailedApi(failApi);

        return new ResponseResult<>(0,"success",trafficStatistics);

    }

    @Override
    public ResponseResult receiveRequestLog(KongHttpLog kongHttpLog) {
        RequestLog request = kongHttpLog.getRequest();
        ResponseLog response = kongHttpLog.getResponse();
        ServiceLog service = kongHttpLog.getService();
        Latencies latencies = kongHttpLog.getLatencies();

        Log log = new Log(request.getMethod(),request.getUri(),Integer.parseInt(request.getSize()),response.getStatus(),Integer.parseInt(response.getSize()),
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

    private List<Integer> getSuccessHttpStatus(){
        LinkedList<Integer> successStatus = new LinkedList<>();
        successStatus.addLast(200);
        successStatus.addLast(302);
        return successStatus;
    }

    private List<Integer> getFailHttpStatus(){
        //429, 404, 500, 501, 502, 503, 504, 505
        LinkedList<Integer> successStatus = new LinkedList<>();
        successStatus.addLast(429);
        successStatus.addLast(404);
        successStatus.addLast(500);
        successStatus.addLast(501);
        successStatus.addLast(502);
        successStatus.addLast(503);
        successStatus.addLast(504);
        successStatus.addLast(505);
        return successStatus;
    }

    private double getAverageResponseTime(Date timestamp){
        List<Object> results = logRepository.findAverageResponseTime(timestamp);
        if (results.size()>0&&results.get(0)!=null){
            return Double.parseDouble(String.valueOf(results.get(0)));
        }else {
            return 0;
        }
    }

    private List<ApiSimpleInfo> getFrequentService(Date timestamp,int num){
        List<ApiSimpleInfo> apiSimpleInfos = new LinkedList<>();
        List<Object[]> results = logRepository.findFrequentService(timestamp);
        if (results.size()>0&&results.get(0).length>0){
            int i = 0;
            while (i<results.size()){
                String serviceId = String.valueOf(results.get(i)[0]);
                int frequency = Integer.parseInt(String.valueOf(results.get(i)[1]));
                Service service = serviceRepository.findServiceById(serviceId);
                if (service!=null){
                    ApiSimpleInfo apiSimpleInfo = new ApiSimpleInfo(serviceId,service.getServiceRoute().getPath());
                    apiSimpleInfo.setFrequency(frequency);
                    apiSimpleInfos.add(apiSimpleInfo);
                    if (apiSimpleInfos.size()>=num){
                        break;
                    }
                }
                i++;
            }
        }
        return apiSimpleInfos;
    }

    private List<ApiSimpleInfo> getTimeConsumingService(Date timestamp,int num){
        List<ApiSimpleInfo> apiSimpleInfos = new LinkedList<>();
        List<Object[]> results = logRepository.findTimeConsumingService(timestamp);
        if (results.size()>0&&results.get(0).length>0){
            int i = 0;
            while (i<results.size()){
                String serviceId = String.valueOf(results.get(i)[0]);
                int responseTime = Integer.parseInt(String.valueOf(results.get(i)[1]));
                Service service = serviceRepository.findServiceById(serviceId);
                if (service!=null){
                    ApiSimpleInfo apiSimpleInfo = new ApiSimpleInfo(serviceId,service.getServiceRoute().getPath());
                    apiSimpleInfo.setResponseTime(responseTime);
                    apiSimpleInfos.add(apiSimpleInfo);
                    if (apiSimpleInfos.size()>=num){
                        break;
                    }
                }
                i++;
            }
        }
        return apiSimpleInfos;
    }

    private List<ApiSimpleInfo> getFailService(Date timestamp,int num){
        List<ApiSimpleInfo> apiSimpleInfos = new LinkedList<>();
        List<Object[]> results = logRepository.findFailService(timestamp,getFailHttpStatus());
        if (results.size()>0&&results.get(0).length>0){
            int i = 0;
            while (i<results.size()){
                String serviceId = String.valueOf(results.get(i)[0]);
                int responseTime = Integer.parseInt(String.valueOf(results.get(i)[1]));
                Service service = serviceRepository.findServiceById(serviceId);
                if (service!=null){
                    ApiSimpleInfo apiSimpleInfo = new ApiSimpleInfo(serviceId,service.getServiceRoute().getPath());
                    apiSimpleInfo.setFailTimes(responseTime);
                    apiSimpleInfos.add(apiSimpleInfo);
                    if (apiSimpleInfos.size()>=num){
                        break;
                    }
                }
                i++;
            }
        }
        return apiSimpleInfos;
    }
}
