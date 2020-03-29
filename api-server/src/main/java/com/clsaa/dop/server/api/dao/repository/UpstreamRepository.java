package com.clsaa.dop.server.api.dao.repository;

import com.clsaa.dop.server.api.dao.entity.Upstream;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UpstreamRepository extends JpaRepository<Upstream,String> {
    Upstream findUpstreamById(String id);
}
