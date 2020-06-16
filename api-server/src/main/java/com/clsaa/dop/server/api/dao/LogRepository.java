package com.clsaa.dop.server.api.dao;

import com.clsaa.dop.server.api.module.po.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface LogRepository extends JpaRepository<Log,String> {
    Page<Log> findAllByTimeAfterAndTimeBeforeAndResponseStatus(Date beginTime, Date endTime, int responseStatus,Pageable pageable);

    Page<Log> findAllByServiceId(String serviceId,Pageable pageable);

    Log findLogById(String id);

    int countLogByTimeAfterAndResponseStatusIn(Date time,List<Integer> responseStatus);

    @Query(nativeQuery = true,value = "select avg(response_timeout) from log where time > ?1 ;")
    List<Object> findAverageResponseTime(Date timestamp);

    @Query(nativeQuery = true,value = "select service_id,count(*) from log where time > ?1 group by service_id order by count(*) desc;")
    List<Object[]> findFrequentService(Date timestamp);

    @Query(nativeQuery = true,value = "select service_id,sum(response_timeout) from log where time > ?1 group by service_id order by sum(log.response_timeout) desc;")
    List<Object[]> findTimeConsumingService(Date timestamp);

    @Query(value = "select service_id,count(*) from log where time > ?1 and response_status in ?2 " +
            "group by service_id order by count(*) desc;",nativeQuery = true)
    List<Object[]> findFailService(Date timestamp,List<Integer> failStatus);

}
