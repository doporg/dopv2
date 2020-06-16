package com.clsaa.dop.server.alert.model.po;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @ClassName StrategyPo
 * @Author 洪铨健
 * @Version 1.0
 * @Describtion 告警策略类
 * @since 2020-03-05
 **/

@Entity
@Getter
@Setter
@Table(name = "Strategy")
public class StrategyPo implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	/**
	 *
	 */
	@Column(name = "userId")
	private Long userId;

	/**
	 * 告警规则列表
	 */
	@JoinColumn(name = "rulePOList")
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<RulePo> rulePOList;

	/**
	 * 告警名称
	 */
	@Column(name = "alertName")
	private String alertName;

	/**
	 * 描述
	 */
	@Column(name = "description")
	private String description;

	/**
	 * 告警级别：一般，严重
	 */
	@Column(name = "level")
	Level level;

	public enum Level{
		Normal,Serve
	}

	/**
	 * 告警状态:启用和禁止
	 */
	@Column(name = "state")
	State state;

	public enum State{
		On,Off
	}

	/**
	 * 通知间隔，以分钟为单位
	 */
	@Column(name = "inform_interval")
	private int inform_interval;

	/**
	 * 告警联系人
	 */
	@Column(name = "contactList")
	@ManyToMany
	private List<ContactPo> contactList;

	/**
	 * 告警联系方式：邮箱，短信，邮箱和短信
	 */
	private ContactWays contactWays;

	public enum ContactWays{

		Email("Email"),
		Message("Message"),
		EmailAndMessage("EmailAndMessage");

		private String ways;
		private ContactWays(String contactWays){
			ways = contactWays;
		}
	}

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


	public StrategyPo() {
	}


}
