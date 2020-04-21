package com.clsaa.dop.server.alert.dao;

import com.clsaa.dop.server.alert.model.po.ContactPo;
import com.clsaa.dop.server.alert.model.po.StrategyPo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * @ClassName ContactDao
 * @Author
 * @Version 1.0
 * @Describtion TODO
 * @return
 * @since 2020-04-03
 **/

@Repository
public interface ContactDao extends JpaRepository<ContactPo, Long>,JpaSpecificationExecutor {
	public ContactPo findByCid(Long id);
//	Page<ContactPo> findAll(Specification<ContactPo> var1, Pageable pageable);
//	Page<ContactPo> findByCuser(Long cuser,Pageable pageable);
}
