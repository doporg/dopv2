package com.clsaa.dop.server.alert.service;

import com.clsaa.dop.server.alert.dao.ContactDao;
import com.clsaa.dop.server.alert.model.po.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

/**
 * @ClassName ContactService
 * @Author
 * @Version 1.0
 * @Describtion TODO
 * @return
 * @since 2020-04-03
 **/

@Service
@Transactional
public class ContactService {
	@Autowired
	ContactDao contactDao;

	void addNewContacts(ArrayList<Contact> contacts){

	}


}
