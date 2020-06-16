package com.clsaa.dop.server.alert.model.vo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @ClassName RulePo
 * @Author 洪铨健
 * @Version 1.0
 * @Describtion TODO
 * @since 2020-03-05
 **/

@Getter
@Setter
public class RuleVo {

	Long rid;

	/**
	 * 告警实例
	 */
	String para;

	/**
	 * 阈值
	 */
	int threshold;

	/**
	 * 条件
	 */
	public Relation relation;

	public enum  Relation{
		greater,smaller
	}

	/**
	 * 统计间隔，秒为单位
	 */
	int duration;

	public RuleVo() {
	}

	public RuleVo(String para, int threshold, Relation relation, int duration) {
		this.para = para;
		this.threshold = threshold;
		this.relation = relation;
		this.duration = duration;
	}
}
