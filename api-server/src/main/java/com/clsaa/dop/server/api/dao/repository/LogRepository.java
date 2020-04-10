package com.clsaa.dop.server.api.dao.repository;

import com.clsaa.dop.server.api.dao.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface LogRepository extends JpaRepository<Log,String> {
    List<Log> findByTimeAfterAndTimeBeforeAndResponseStatusOrderByTimeDesc(Date beginTime,Date endTime,int responseStatus);

    List<Log> findTop10ByOrderByTimeDesc();

    Log findLogById(String id);
}
