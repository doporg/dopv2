package com.clsaa.dop.server.link.service;

import com.clsaa.dop.server.link.dao.BindDao;
import com.clsaa.dop.server.link.dao.NoticeDao;
import com.clsaa.dop.server.link.dao.RecordDao;
import com.clsaa.dop.server.link.enums.MonitorState;
//import com.clsaa.dop.server.link.feign.MessageInterface;
import com.clsaa.dop.server.link.model.po.*;
import com.clsaa.dop.server.link.model.vo.monitor.*;
import com.clsaa.dop.server.link.util.BeanUtils;
import com.clsaa.rest.result.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@EnableScheduling
public class MonitorBindService {

    private static final String fail = "Fail";
    private static final String success = "Success";
    private static final String repeat = "Repeat";

    @Autowired
    private BindDao bindDao;

    @Autowired
    private MonitorRecordService recordService;

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private TraceService traceService;

    public Pagination<BindVO> getPageableList(long cuser, int pageNo, int pageSize, String keyword) {
        Pagination<BindVO> pagination = new Pagination<>();

        pagination.setPageNo(pageNo);
        pagination.setPageSize(pageSize);

        int count = bindDao.countBindByCuserAndTitleLikeAndIsDeleteFalse(cuser, "%"+keyword+"%");
        List<Bind> res = bindDao.findByCuserAndTitleLike(cuser, pagination.getRowOffset(), pageSize, keyword);
        pagination.setTotalCount(count);
        pagination.setPageList(convertToVo(res));
        return pagination;
    }

    public BindInfo findById(long bid) {
        Bind bind = bindDao.findById(bid);
        if (null == bind) {
            return null;
        }
        List<MonitorRecord> recordList = recordService.getRecordsByBid(bid);
        List<Notice> noticeList = noticeService.getNoticeListByBid(bid);

        BindInfo bindInfo = BeanUtils.convertType(bind, BindInfo.class);
        bindInfo.setRecords(recordList);
        bindInfo.setNotices(noticeList);
        return bindInfo;
    }

    public void add(CreateMonitorBind createMonitorBind) {
        Bind create = BeanUtils.convertType(createMonitorBind, Bind.class);
        create.setState(MonitorState.FREE);
        create.setCtime(new Date());
        create.setDelete(false);
        System.out.println("create bind: " + create.toString());
        bindDao.save(create);
    }

    public StartBindRes start(long bid) {
        Bind bind = bindDao.findById(bid);
        if (null == bind) {
            return new StartBindRes(fail);
        }
        // 先判断同项目同服务下是否已经存在正在运行的监控
        Bind repeatBind = bindDao.checkRepeat(bind.getProjectId(), bind.getService(), MonitorState.RUNNING);
        if (null != repeatBind) {
            return new StartBindRes(repeat, repeatBind.getTitle(), repeatBind.getCuserName());
        }
        System.out.println("start bind: " + bid);
        bindDao.startBind(bid);
        return new StartBindRes(success);
    }

    public void stop(long bid) {
        bindDao.stopBind(bid);
    }

    public void delete(long bid) {
        bindDao.delete(bid);
    }

    public void modify(ModifyMonitorBind modifyMonitorBind) {
        Bind bind = BeanUtils.convertType(modifyMonitorBind, Bind.class);
        bind.setState(MonitorState.FREE);
        bind.setDelete(false);
        bindDao.save(bind);
    }

    private List<BindVO> convertToVo(List<Bind> binds) {
        return binds.stream()
                .map(bind -> BeanUtils.convertType(bind, BindVO.class))
                .collect(Collectors.toList());
    }

    private static final Integer RATE = 1000 * 60 * 30;

    @Autowired
    private RecordDao recordDao;
//    @Autowired
//    private MessageInterface messageInterface;
    @Autowired
    private NoticeDao noticeDao;

    private static final String FromEmail = "";
    private static final String EmailSubject = "";


    @Scheduled(fixedRate = 1800000)
    public void monitor() {
        Date currentTime = new Date();
        // 读取正在运行的监控列表
        List<Bind> runningBinds = bindDao.findAllByStateEquals(MonitorState.RUNNING);
        System.out.println("正在运行的监控----------------\n" + runningBinds);
        for (Bind bind : runningBinds) {
            // 调用 TraceService 中的分析方法
            double currentErrorRate = traceService.calculateRate(bind.getProjectId(), bind.getService(), currentTime);
            // 持久化分析数据
            MonitorRecord newRecord = new MonitorRecord(
                    (long) 0, bind.getBid(), currentErrorRate, bind.getThreshold(),currentTime);
            recordDao.save(newRecord);
            // 超过阈值，发送通知
            if (currentErrorRate >= bind.getThreshold()) {
                String context = buildContext(bind, currentErrorRate, currentTime);
                Notice notice = buildNotice(bind, currentErrorRate, currentTime,context);
                noticeDao.save(notice);
                // 构造邮件内容
                // 调用message-server服务，发送邮件
//                messageInterface.addEmailV2(FromEmail, bind.getNotifiedEmail(), EmailSubject, context);
            }
        }
    }
    Notice buildNotice(Bind bind, double currentRate, Date endTs, String context) {
        return new Notice(0, bind.getBid(), bind.getCuser(), bind.getCuserName(),endTs,
                currentRate, bind.getNotifiedEmail(), context);
    }
    String buildContext(Bind bind, double currentRate, Date time) {
        System.out.println(bind.toString() + currentRate + time);
        return  "";
    }

}
