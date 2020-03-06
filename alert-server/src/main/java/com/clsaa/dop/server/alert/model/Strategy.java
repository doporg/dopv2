package com.clsaa.dop.server.alert.model;

import java.util.List;

/**
 * @ClassNameStrategy
 * @Author 洪铨健
 * @Version 1.0
 * @Describtion
 * @since 2020-03-05
 **/
public class Strategy {
	private List<Rule> ruleList;
	private String name;
	private String alert;
	private String expr;
	private int duration;
	private String level;
	private Contact contact;

	public Strategy(List<Rule> ruleList, String name, String alert, String expr, int duration, String level) {
		this.ruleList = ruleList;
		this.name = name;
		this.alert = alert;
		this.expr = expr;
		this.duration = duration;
		this.level = level;
	}

	public List<Rule> getRuleList() {
		return ruleList;
	}

	public void setRuleList(List<Rule> ruleList) {
		this.ruleList = ruleList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAlert() {
		return alert;
	}

	public void setAlert(String alert) {
		this.alert = alert;
	}

	public String getExpr() {
		return expr;
	}

	public void setExpr(String expr) {
		this.expr = expr;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
}
