package com.clsaa.dop.server.alert.model.po;


import com.clsaa.dop.server.alert.model.vo.ContactVo;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
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

@Entity
@Getter
@Setter
@Table(name = "Contact")
public class ContactPo implements Serializable{

	@Id
	@Column(name = "cid")
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private Long cid;

	@Column(name = "name")
	private String name;

	@Column(name = "mail")
	private String mail;

	@Column(name = "phone")
	private String phone;

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


	public ContactPo() {

	}

	public ContactPo(Long cid, String name, String mail, String phone, LocalDateTime ctime, LocalDateTime mtime, Long cuser, Long muser, Boolean deleted) {
		this.cid = cid;
		this.name = name;
		this.mail = mail;
		this.phone = phone;
		this.ctime = ctime;
		this.mtime = mtime;
		this.cuser = cuser;
		this.muser = muser;
		this.deleted = deleted;
	}

	public void setNewContact(ContactVo contactVo, Long user){
		this.name = contactVo.getName();
		this.mail = contactVo.getMail();
		this.phone = contactVo.getPhone();
		this.ctime = LocalDateTime.now();
		this.mtime = this.ctime;
		this.cuser = user;
		this.muser =this.cuser;
		this.deleted = false;
	}


	public ContactVo transferToVo(){
		return new ContactVo(cid,name,mail,phone,mtime);
	}

	@Override
	public String toString() {
		return "ContactPo{" +
				"cid=" + cid +
				", name='" + name + '\'' +
				", mail='" + mail + '\'' +
				", phone='" + phone + '\'' +
				", ctime=" + ctime +
				", mtime=" + mtime +
				", cuser=" + cuser +
				", muser=" + muser +
				", deleted=" + deleted +
				'}';
	}
}
