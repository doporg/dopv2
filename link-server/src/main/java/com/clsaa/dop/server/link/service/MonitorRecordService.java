package com.clsaa.dop.server.link.service;

import com.clsaa.dop.server.link.dao.RecordDao;
import com.clsaa.dop.server.link.model.po.MonitorRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MonitorRecordService {

    @Autowired
    private RecordDao recordDao;

    public List<MonitorRecord> getRecordsByBid(Long bid) {
        return recordDao.findByBidOrderByEndTsDescLimit(bid, 10);
    }
}
