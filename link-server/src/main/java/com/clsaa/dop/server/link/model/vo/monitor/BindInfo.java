package com.clsaa.dop.server.link.model.vo.monitor;

import com.clsaa.dop.server.link.enums.MonitorState;
import com.clsaa.dop.server.link.model.po.MonitorRecord;
import com.clsaa.dop.server.link.model.po.Notice;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BindInfo {

    private long bid;

    private String title; // 给这个监控起个名字，做个简单说明

    private long cuser;// 创建者的id

    private String cuserName;//创建者的name

    private long projectId;

    private String projectTitle;

    private String notifiedUid; //被通知者的id

    private String notifiedName; //被通知者的name

    private String notifiedEmail; //被通知者的邮箱

    private String service;//这个监控要关注的微服务，每条数据只能绑定一个微服务

    private double threshold;// 阈值 单位时间内调用链n次调用>=m次出错通知

    private MonitorState state;

    private Date ctime; //创建时间

    private List<MonitorRecord> records;

    private List<Notice> notices;
}
