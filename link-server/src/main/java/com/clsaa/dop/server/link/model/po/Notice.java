package com.clsaa.dop.server.link.model.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long nid;

    private long bid;

    private long cuser; //流水线发起者的id

    private String cuserName;

    private Date time; //通知时间

    private double errorRate; // 错误率

    private String email; //发送到邮箱

    // 说明，如：由A人员创建的编号为1的监控流水线发现项目XX的服务A和服务B在xx时xx分到yy时yy分错误率为%%，超过阈值
    private String description;

}
