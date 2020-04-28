package com.clsaa.dop.server.link.dao;

import com.clsaa.dop.server.link.model.po.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeDao extends JpaRepository<Notice, Long> {

    List<Notice> findByCuserOrderByNid(long cuser);
}
