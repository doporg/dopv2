package com.clsaa.dop.server.alert.controller;

import com.clsaa.dop.server.alert.model.vo.ContactVo;
import com.clsaa.dop.server.alert.service.ContactService;
import com.clsaa.rest.result.Pagination;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName ContactController
 * @Author
 * @Version 1.0
 * @Describtion TODO
 * @return
 * @since 2020-03-06
 **/

@CrossOrigin
@RestController
@RequestMapping("/alert/contact")
public class ContactController {

	@Autowired
	ContactService contactService;


	@GetMapping("/page")
	public Pagination<ContactVo> getContactPagination(@RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
	                                                  @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize){
		return contactService.getContactPagination(pageNo, pageSize,(long)1);
	}

	@PostMapping("/info")
	public void addNewContact(@ApiParam(value = "name") @RequestParam(value = "name") String name,
	                          @ApiParam(value = "mail") @RequestParam(value = "mail") String mail,
	                          @ApiParam(value = "phone") @RequestParam(value = "phone") String phone,
	                          @ApiParam(value = "remark") @RequestParam(value = "remark") String remark,
	                          @ApiParam(value = "用户id") @RequestHeader("x-login-user") Long userId){
		ContactVo contactVo = new ContactVo(name,mail,phone,remark);
		contactService.addNewContact(contactVo,userId);
	}

	@GetMapping("/info")
	public ContactVo getContactInfo(@ApiParam(value = "contactId") @RequestParam(value = "contactId") Long contactId){
		return contactService.getContactById(contactId);
	}

	@PutMapping("/info")
	public void modifyContact(@ApiParam(value = "contactVo") @RequestBody ContactVo contactVo){
		contactService.modifyContact(contactVo);
	}

	@PostMapping("/info/edit")
	public void editContact(@ApiParam(value = "contactVo") @RequestBody ContactVo contactVo){
		System.out.println(contactVo.toString());
		contactService.modifyContact( contactVo);
	}

	@DeleteMapping("contacts")
	public void deleteContacts(@ApiParam(value = "contactIdList") @RequestParam(value = "contact") List<Long> contactIdList){
		contactService.deleteContacts(contactIdList);
	}
}
