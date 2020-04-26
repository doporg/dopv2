package com.clsaa.dop.server.api.dao;

import com.clsaa.dop.server.api.module.po.CurrentLimitPolicy;
import com.clsaa.dop.server.api.module.po.Service;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CurrentLimitPolicyRepository extends JpaRepository<CurrentLimitPolicy,String> {
    CurrentLimitPolicy findCurrentLimitPolicyById(String id);

    CurrentLimitPolicy findByName(String name);

    List<CurrentLimitPolicy> findByNameStartingWith(String name);
}
