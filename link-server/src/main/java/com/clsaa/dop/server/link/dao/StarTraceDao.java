package com.clsaa.dop.server.link.dao;

import com.clsaa.dop.server.link.model.po.StarTrace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StarTraceDao extends JpaRepository<StarTrace, Long> {

    List<StarTrace> findByUserId(long userId);
}
