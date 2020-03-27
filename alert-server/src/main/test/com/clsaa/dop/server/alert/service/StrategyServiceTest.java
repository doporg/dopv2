package com.clsaa.dop.server.alert.service;

import com.clsaa.dop.server.alert.model.po.Contact;
import com.clsaa.dop.server.alert.model.po.Rule;
import com.clsaa.dop.server.alert.model.po.Strategy;
import com.clsaa.dop.server.alert.service.StrategyService;
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
	* Method: addNewStrategy(Strategy strategy)
	*
	*/
	@Test
	public void testAddNewStrategy() throws Exception {
	//TODO: Test goes here...
		Strategy strategy = new Strategy();
		strategy.setAlertName("test");
		strategy.setContactWays(Strategy.ContactWays.EmailAndMessage);
		strategy.setUserId((long) 11);
		strategy.setDescription("add test");
		strategy.setInform_interval(10);
		strategy.setLevel(Strategy.Level.Normal);
		strategy.setState(Strategy.State.Off);

		ArrayList<Rule> rules = new ArrayList<>();
		rules.add(new Rule("rule1",50, Rule.Relation.smaller,10));

		ArrayList<Contact> contacts = new ArrayList<>();
		contacts.add(new Contact((long) 1,"c11","123123","123@123"));

		strategy.setRuleList(rules);
		strategy.setContactList(contacts);
		strategyService.addNewStrategy(strategy);

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
	* Method: modifyStrategy(Strategy strategy)
	*
	*/
	@Test
	public void testModifyStrategy() throws Exception {
	//TODO: Test goes here...
	}


} 
