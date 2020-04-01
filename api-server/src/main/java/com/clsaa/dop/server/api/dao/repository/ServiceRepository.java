package com.clsaa.dop.server.api.dao.repository;

import com.clsaa.dop.server.api.dao.entity.Service;
import com.clsaa.dop.server.api.dao.entity.ServiceRoute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceRepository extends JpaRepository<Service,String> {
    Service findByName(String name);

    Service findServiceById(String id);

    List<Service> findByServiceRoute(ServiceRoute serviceRoute);
}
