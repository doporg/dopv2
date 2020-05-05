package com.clsaa.dop.server.api.dao;

import com.clsaa.dop.server.api.module.po.Upstream;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UpstreamRepository extends JpaRepository<Upstream,String> {
    Upstream findUpstreamById(String id);

    Upstream findUpstreamByName(String name);
}
