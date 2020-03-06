package com.clsaa.dop.server.alert.service;

import com.clsaa.dop.server.alert.model.Rule;
import com.clsaa.dop.server.alert.model.Strategy;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassNameStrategyService
 * @Author 洪铨健
 * @Version 1.0
 * @Describtion TODO
 * @since 2020-03-05
 **/
public interface StrategyService {
	public void addNewStrategy();
	public ArrayList<Strategy> getStrategyById(String id);
	public void deleteStrategy(String sid);
	public void modifyStrategy(Strategy strategy);

}
