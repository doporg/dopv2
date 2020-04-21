package com.clsaa.dop.server.alert.service;

import com.clsaa.dop.server.alert.dao.StrategyDao;
import com.clsaa.dop.server.alert.model.po.StrategyPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

/**
 * @ClassName StrategyService
 * @Author 洪铨健
 * @Version 1.0
 * @Describtion TODO
 * @since 2020-03-07
 **/

@Service
@Transactional

public class StrategyService {
	@Autowired
	StrategyDao strategyDao;

	public void addNewStrategy(StrategyPo strategyPo) {
		strategyDao.save(strategyPo);
	}

	public ArrayList<StrategyPo> getStrategyById(Long userId) {
		return strategyDao.getStrategiesByUserId(userId);
	}

	public void deleteStrategy(Long sid) {
		strategyDao.deleteById(sid);
	}

	public void updateStrategy(StrategyPo strategyPo) {
		strategyDao.save(strategyPo);
	}
}