package com.clsaa.dop.server.api.dao;

import com.clsaa.dop.server.api.module.po.CurrentLimitPolicy;
import com.clsaa.dop.server.api.module.po.Service;
import com.clsaa.dop.server.api.module.po.ServiceRoute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceRepository extends JpaRepository<Service,String> {
    Service findByName(String name);

    Service findServiceById(String id);

    List<Service> findByServiceRoute(ServiceRoute serviceRoute);

    List<Service> findByCurrentLimitPolicy(CurrentLimitPolicy currentLimitPolicy);
}
