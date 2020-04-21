package com.clsaa.dop.server.alert.service;

import com.clsaa.dop.server.alert.model.po.ContactPo;
import com.clsaa.dop.server.alert.model.po.RulePo;
import com.clsaa.dop.server.alert.model.po.StrategyPo;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

/**
* StrategyService Tester. 
* 
* @author <Authors name> 
* @since <pre>���� 27, 2020</pre> 
* @version 1.0 
*/

@RunWith(SpringRunner.class)
@SpringBootTest
public class StrategyServiceTest {
	@Autowired
	StrategyService strategyService;

	@Before
	public void before() throws Exception {
	}

	@After
	public void after() throws Exception {
	}

	/**
	*
	* Method: addNewStrategy(StrategyPo strategy)
	*
	*/
	@Test
	public void testAddNewStrategy() throws Exception {
	//TODO: Test goes here...
		StrategyPo strategyPo = new StrategyPo();
		strategyPo.setAlertName("test");
		strategyPo.setContactWays(StrategyPo.ContactWays.EmailAndMessage);
		strategyPo.setUserId((long) 11);
		strategyPo.setDescription("add test");
		strategyPo.setInform_interval(10);
		strategyPo.setLevel(StrategyPo.Level.Normal);
		strategyPo.setState(StrategyPo.State.Off);

		ArrayList<RulePo> rulePOS = new ArrayList<>();
		rulePOS.add(new RulePo("rule1",50, RulePo.Relation.smaller,10));

		ArrayList<ContactPo> contacts = new ArrayList<>();
//		contacts.add(new ContactPo((long) 1,"c11","123123","123@123"));

		strategyPo.setRulePOList(rulePOS);
		strategyPo.setContactList(contacts);
		strategyService.addNewStrategy(strategyPo);

	}

	/**
	*
	* Method: getStrategyById(String id)
	*
	*/
	@Test
	public void testGetStrategyById() throws Exception {
	//TODO: Test goes here...
	}

	/**
	*
	* Method: deleteStrategy(String sid)
	*
	*/
	@Test
	public void testDeleteStrategy() throws Exception {
	//TODO: Test goes here...
	}

	/**
	*
	* Method: modifyStrategy(StrategyPo strategy)
	*
	*/
	@Test
	public void testModifyStrategy() throws Exception {
	//TODO: Test goes here...
	}


} 
