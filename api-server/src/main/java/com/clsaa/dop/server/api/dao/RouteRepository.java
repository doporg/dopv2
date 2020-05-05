package com.clsaa.dop.server.api.dao;

import com.clsaa.dop.server.api.module.po.Route;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RouteRepository extends JpaRepository<Route,String> {
    Route findRouteById(String id);

    Route findRouteByServiceId(String id);

    List<Route> findByOnline(boolean online);

    void deleteByServiceId(String id);
}
