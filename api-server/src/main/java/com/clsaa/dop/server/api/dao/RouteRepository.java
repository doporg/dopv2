package com.clsaa.dop.server.api.dao;

import com.clsaa.dop.server.api.module.po.Route;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RouteRepository extends JpaRepository<Route,String> {
    Route findRouteByServiceId(String id);

    Page<Route> findAllByOnline(boolean online, Pageable pageable);
}
