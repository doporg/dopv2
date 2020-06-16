package com.clsaa.dop.server.alert.model.po;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @ClassName RulePo
 * @Author 洪铨健
 * @Version 1.0
 * @Describtion TODO
 * @since 2020-03-05
 **/

@Entity
@Getter
@Setter
@Table(name = "Rule")
public class RulePo implements Serializable{

	@Id
	@Column(name = "rid")
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
	public Relation relation;

	public enum  Relation{
		greater,smaller
	}

	/**
	 * 统计间隔，秒为单位
	 */
	@Column(name = "duration")
	int duration;

	/**
	 * 创建时间
	 */
	@Column(name = "ctime")
	private LocalDateTime ctime;

	/**
	 * 修改时间
	 */
	@Column(name = "mtime")
	private LocalDateTime mtime;

	/**
	 * 创建人
	 */
	@Column(name = "cuser")
	private Long cuser;

	/**
	 * 修改人
	 */
	@Column(name = "muser")
	private Long muser;

	/**
	 * 删除标记
	 */
	@Column(name = "is_deleted")
	private Boolean deleted;


	public RulePo() {
	}

	public RulePo(String para, int threshold, Relation relation, int duration) {
		this.para = para;
		this.threshold = threshold;
		this.relation = relation;
		this.duration = duration;
	}
}
