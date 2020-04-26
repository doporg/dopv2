package com.clsaa.dop.server.alert.model.vo;


import com.clsaa.dop.server.alert.model.po.ContactPo;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @ClassName ContactPo
 * @Author 洪铨健
 * @Version 1.0
 * @Describtion 联系人的类，存储联系人联系方式
 * @return
 * @since 2020-03-06
 **/

@Getter
@Setter
public class ContactVo {

	private Long id;

	private String name;

	private String mail;

	private String phone;

	private String remark;

	private LocalDateTime mtime;

	public ContactVo() {
	}

	public ContactVo(Long id, String name, String mail, String phone, String remark, LocalDateTime mtime) {
		this.id = id;
		this.name = name;
		this.mail = mail;
		this.phone = phone;
		this.mtime = mtime;
		this.remark = remark;
	}

	public ContactVo(String name, String mail, String phone,String remark) {
		this.name = name;
		this.mail = mail;
		this.phone = phone;
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "ContactVo{" +
				"id=" + id +
				", name='" + name + '\'' +
				", mail='" + mail + '\'' +
				", phone='" + phone + '\'' +
				", remark='" + remark + '\'' +
				", mtime=" + mtime +
				'}';
	}
}
