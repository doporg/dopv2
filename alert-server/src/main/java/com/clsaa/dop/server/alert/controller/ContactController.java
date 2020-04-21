package com.clsaa.dop.server.alert.controller;

import com.clsaa.dop.server.alert.model.vo.ContactVo;
import com.clsaa.dop.server.alert.service.ContactService;
import com.clsaa.rest.result.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

}
