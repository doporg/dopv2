package com.clsaa.dop.server.link.dao;

import com.clsaa.dop.server.link.model.po.MonitorRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecordDao extends JpaRepository<MonitorRecord, Long> {

    List<MonitorRecord> findAllByBid(long bid);

    @Query(value = "select * from monitor_record where bid=:bid order by end_ts desc limit :limit", nativeQuery = true)
    List<MonitorRecord> findByBidOrderByEndTsDescLimit(long bid, int limit);
}
