package com.clsaa.dop.server.testing.dao;


import com.clsaa.dop.server.testing.model.po.UserProjectMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserProjectMappingRepository extends JpaRepository<UserProjectMapping,Long> {

    /**
     * 通过userId得到所有的project
     * @param userId
     * @return {@link UserProjectMapping}
     */
    List<UserProjectMapping> findUserProjectMappingsByUserId(Long userId);
}
