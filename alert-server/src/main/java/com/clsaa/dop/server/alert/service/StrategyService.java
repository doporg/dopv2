package com.clsaa.dop.server.alert.service;

import com.clsaa.dop.server.alert.dao.StrategyDao;
import com.clsaa.dop.server.alert.model.po.Strategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

/**
 * @ClassName StrategyService
 * @Author
 * @Version 1.0
 * @Describtion TODO
 * @return
 * @since 2020-03-07
 **/

@Service

@Transactional
public class StrategyService {
	@Autowired
	StrategyDao strategyDao;

	public void addNewStrategy(Strategy strategy) {
		strategyDao.save(strategy);
	}

	public ArrayList<Strategy> getStrategyById(String id) {
		return null;
	}

	public void deleteStrategy(String sid) {

	}

	public void modifyStrategy(Strategy strategy) {

	}
}
