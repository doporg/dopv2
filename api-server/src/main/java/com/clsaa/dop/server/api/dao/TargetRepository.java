package com.clsaa.dop.server.api.dao;

import com.clsaa.dop.server.api.module.po.Target;
import com.clsaa.dop.server.api.module.po.Upstream;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TargetRepository extends JpaRepository<Target,String> {

    void deleteByUpstream(Upstream upstream);

    List<Target> findByUpstream(Upstream upstream);
}
