package com.clsaa.dop.server.api.dao;

import com.clsaa.dop.server.api.module.po.LimitPolicy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface LimitPolicyRepository extends JpaRepository<LimitPolicy,String> {
    LimitPolicy findLimitPolicyById(String id);

    LimitPolicy findByName(String name);

    List<LimitPolicy> findByNameStartingWith(String name);
}
