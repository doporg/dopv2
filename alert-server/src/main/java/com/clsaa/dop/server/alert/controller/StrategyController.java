package com.clsaa.dop.server.alert.controller;

import com.clsaa.dop.server.alert.model.po.Strategy;
import com.clsaa.dop.server.alert.service.StrategyService;
import com.clsaa.rest.result.Pagination;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * @ClassName StrategyController
 * @Author
 * @Version 1.0
 * @Describtion TODO
 * @return
 * @since 2020-03-06
 **/

@CrossOrigin
@Controller
@RequestMapping("/alert")
public class StrategyController {

	@Autowired
	StrategyService strategyService;

	@PostMapping("/test")
	public void testHaveUserIdHeader(@RequestHeader(HttpHeaders.X_LOGIN_USER) Long id) {
		System.out.println(id);
	}


	@ApiOperation(value = "创建一个新策略",notes = "")
	@PostMapping("/strategy")
	public void addBranch(@ApiParam(value = "用户名") @PathVariable("username") String username,
	                      @ApiParam(value = "项目名") @PathVariable("projectname") String projectname,
	                      @ApiParam(value = "新的分支名") @RequestParam("branch") String branch,
	                      @ApiParam(value = "创建自分支、标签或者commit SHA") @RequestParam("ref") String ref,
	                      @ApiParam(value = "用户id") @RequestHeader("x-login-user") Long userId){
		String id=username+"/"+projectname;

//		strategyService.addNewStrategy();
	}





	/**
	 * 自定义HTTP请求头
	 *
	 * @author joyren
	 */
	public interface HttpHeaders {
		/**
		 * 用户登录Token请求头
		 */
		String X_LOGIN_TOKEN = "x-login-token";
		/**
		 * 登录用户id
		 */
		String X_LOGIN_USER = "x-login-user";
	}
}
