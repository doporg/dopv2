package com.clsaa.dop.server.api.dao;

import com.clsaa.dop.server.api.module.po.ServiceRoute;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceRouteRepository extends JpaRepository<ServiceRoute,String> {
    ServiceRoute findServiceRouteById(String id);

    ServiceRoute findByName(String name);

    Page<ServiceRoute> findAllByType(String type, Pageable pageable);

    List<ServiceRoute> findByType(String type);

    List<ServiceRoute> findByNameStartingWith(String value);
}
