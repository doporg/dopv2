package com.clsaa.dop.server.api.dao.repository;

import com.clsaa.dop.server.api.dao.entity.ServiceRoute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceRouteRepository extends JpaRepository<ServiceRoute,String> {
    ServiceRoute findServiceRouteById(String id);

    ServiceRoute findByName(String name);

    List<ServiceRoute> findByType(String type);
}
