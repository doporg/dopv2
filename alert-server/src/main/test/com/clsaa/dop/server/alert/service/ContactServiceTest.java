package com.clsaa.dop.server.alert.service;

import com.clsaa.dop.server.alert.dao.ContactDao;
import com.clsaa.dop.server.alert.model.po.ContactPo;
import com.clsaa.dop.server.alert.model.po.RulePo;
import com.clsaa.dop.server.alert.model.po.ContactPo;
import com.clsaa.dop.server.alert.model.vo.ContactVo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
* ContactService Tester. 
* 
* @author <Authors name> 
* @since <pre>���� 27, 2020</pre> 
* @version 1.0 
*/

@RunWith(SpringRunner.class)
@SpringBootTest
public class ContactServiceTest {
	@Autowired
	ContactService contactService;

	@Before
	public void before() throws Exception {
	}

	@After
	public void after() throws Exception {
	}

	/**
	*
	* Method: addNewContact(ContactPo contact)
	*
	*/
	@Test
	public void testAddNewContact() throws Exception {
	//TODO: Test goes here...
		contactService.addNewContact(new ContactVo("张三","wewewe@1","1234123",""),(long)1);
		contactService.addNewContact(new ContactVo("李四","wewewe@2","1234123",""),(long)1);
		contactService.addNewContact(new ContactVo("王五","wewewe@3","1234123",""),(long)1);
		contactService.addNewContact(new ContactVo("胡少","wewewe@1","1234123",""),(long)1);
		contactService.addNewContact(new ContactVo("何少","wewewe@2","1234123",""),(long)1);
		contactService.addNewContact(new ContactVo("王小波","wewewe@3","1234123",""),(long)2);
	}



	/**
	*
	* Method: getContactById(String id)
	*
	*/
	@Test
	public void testGetContactById() throws Exception {
	//TODO: Test goes here...
		contactService.getContactPagination(1,10,(long)1);
	}

	/**
	*
	* Method: deleteContact(String sid)
	*
	*/
	@Test
	public void testDeleteContact() throws Exception {
	//TODO: Test goes here...
	}

	/**
	*
	* Method: modifyContact(ContactPo contact)
	*
	*/
	@Test
	public void testModifyContact() throws Exception {
	//TODO: Test goes here...
		contactService.modifyContact(new ContactVo((long)1,"ww2","wewewe@1","123232323","", LocalDateTime.now()));

		contactService.modifyContact(new ContactVo((long)4,"ww2","wewewe@1","123232323","", LocalDateTime.now()));
	}


} 
