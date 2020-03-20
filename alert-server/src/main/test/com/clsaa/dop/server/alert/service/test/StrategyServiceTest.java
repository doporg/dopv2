package com.clsaa.dop.server.alert.service.test;

import com.clsaa.dop.server.alert.model.po.Contact;
import com.clsaa.dop.server.alert.model.po.Rule;
import com.clsaa.dop.server.alert.model.po.Strategy;
import com.clsaa.dop.server.alert.service.StrategyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StrategyServiceTest {

	@Autowired
	StrategyService strategyService;

	@Test
	public void addNewStrategy() {
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

	@Test
	public void getStrategyById() {
	}

	@Test
	public void deleteStrategy() {
	}

	@Test
	public void modifyStrategy() {
	}
}