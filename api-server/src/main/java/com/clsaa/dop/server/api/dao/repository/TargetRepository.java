package com.clsaa.dop.server.api.dao.repository;

import com.clsaa.dop.server.api.dao.entity.Target;
import com.clsaa.dop.server.api.dao.entity.Upstream;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TargetRepository extends JpaRepository<Target,String> {

    void deleteByUpstream(Upstream upstream);

    List<Target> findByUpstream(Upstream upstream);
}
