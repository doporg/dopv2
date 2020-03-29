package com.clsaa.dop.server.api.dao.repository;

import com.clsaa.dop.server.api.dao.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<Service,String> {
    Service findByName(String name);

    Service findServiceById(String id);
}
