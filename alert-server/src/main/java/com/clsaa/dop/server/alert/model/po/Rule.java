package com.clsaa.dop.server.alert.model.po;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @ClassName Rule
 * @Author 洪铨健
 * @Version 1.0
 * @Describtion TODO
 * @since 2020-03-05
 **/

@Entity
@Getter
@Setter
@Table(name = "Rule")
public class Rule implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	Long rid;

	/**
	 * 告警实例
	 */
	@Column(name = "para")
	String para;

	/**
	 * 阈值
	 */
	@Column(name = "threshold")
	int threshold;

	/**
	 * 条件
	 */
	@Column(name = "relation")
	Relation relation;

	public enum  Relation{
		greater,smaller
	}

	/**
	 * 统计间隔，秒为单位
	 */
	@Column(name = "duration")
	int duration;

	public Rule() {
	}

	public Rule(String para, int threshold, Relation relation, int duration) {
		this.para = para;
		this.threshold = threshold;
		this.relation = relation;
		this.duration = duration;
	}
}
