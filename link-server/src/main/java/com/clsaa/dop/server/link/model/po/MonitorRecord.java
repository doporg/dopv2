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
public class MonitorRecord {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long rid;

    private long bid;

    private double errorRate;

    private double threshold;

    private Date endTs; // 计算时间，record记录的是 ctime30分钟之前到ctime的数据
}
