package com.clsaa.dop.server.alert.model.po;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @ClassName Contact
 * @Author 洪铨健
 * @Version 1.0
 * @Describtion 联系人的类，存储联系人联系方式
 * @return
 * @since 2020-03-06
 **/

@Entity
@Getter
@Setter
@Table(name = "Contact")
public class Contact implements Serializable{

	@Id
	@Column(name = "cid")
	private Long cid;

	@Column(name = "name")
	private String name;

	@Column(name = "mail")
	private String mail;

	@Column(name = "phone")
	private String phone;

	public Contact() {
	}

	public Contact(Long cid, String name, String mail, String phone) {
		this.cid = cid;
		this.name = name;
		this.mail = mail;
		this.phone = phone;
	}
}
