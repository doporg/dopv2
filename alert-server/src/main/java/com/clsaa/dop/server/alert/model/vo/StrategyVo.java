package com.clsaa.dop.server.alert.model.vo;

import com.clsaa.dop.server.alert.model.po.ContactPo;
import com.clsaa.dop.server.alert.model.po.RulePo;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @ClassName StrategyPo
 * @Author 洪铨健
 * @Version 1.0
 * @Describtion 告警策略类
 * @since 2020-03-05
 **/

@Getter
@Setter
public class StrategyVo {

	private Long id;

	/**
	 *
	 */
	private Long userId;

	/**
	 * 告警规则列表
	 */
	private List<RulePo> rulePOList;

	/**
	 * 告警名称
	 */
	private String alertName;

	/**
	 * 描述
	 */
	private String description;

	/**
	 * 告警级别：一般，严重
	 */
	Level level;

	public enum Level{
		Normal,Serve
	}

	/**
	 * 告警状态:启用和禁止
	 */
	State state;

	public enum State{
		On,Off
	}

	/**
	 * 通知间隔，以分钟为单位
	 */
	private int inform_interval;

	/**
	 * 告警联系人
	 */
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

	public StrategyVo() {
	}


}
