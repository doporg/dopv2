package com.clsaa.dop.server.alert.dao;

import com.clsaa.dop.server.alert.model.po.Strategy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @ClassName ContactDao
 * @Author
 * @Version 1.0
 * @Describtion TODO
 * @return
 * @since 2020-04-03
 **/

@Repository
public interface ContactDao extends JpaRepository<Strategy, Long> {

}
