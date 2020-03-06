package com.clsaa.dop.server.alert.model;

/**
 * @ClassNameContact
 * @Author
 * @Version 1.0
 * @Describtion TODO
 * @return
 * @since 2020-03-06
 **/
public class Contact {
	private String name;
	private String mail;
	private String phone;

	public Contact(String name, String mail, String phone) {
		this.name = name;
		this.mail = mail;
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
