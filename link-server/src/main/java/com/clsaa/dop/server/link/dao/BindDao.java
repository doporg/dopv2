package com.clsaa.dop.server.link.dao;

import com.clsaa.dop.server.link.model.po.Bind;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BindDao extends JpaRepository<Bind, Long> {

    List<Bind> findByCuser(long cuser);

}
