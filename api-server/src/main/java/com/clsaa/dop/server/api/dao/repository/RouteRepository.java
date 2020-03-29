package com.clsaa.dop.server.api.dao.repository;

import com.clsaa.dop.server.api.dao.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RouteRepository extends JpaRepository<Route,String> {
    Route findRouteById(String id);

    Route findRouteByServiceId(String id);

    void deleteByServiceId(String id);
}
