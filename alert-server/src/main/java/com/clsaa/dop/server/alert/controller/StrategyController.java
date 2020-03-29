package com.clsaa.dop.server.alert.controller;

import com.clsaa.dop.server.alert.model.po.Strategy;
import com.clsaa.dop.server.alert.service.StrategyService;
import com.clsaa.rest.result.Pagination;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @ClassName StrategyController
 * @Author
 * @Version 1.0
 * @Describtion TODO
 * @return
 * @since 2020-03-06
 **/

@Controller
@RequestMapping("/v2/alert")
public class StrategyController {

	@Autowired
	StrategyService strategyService;

//	@ApiOperation(value = "分页查询告警策略信息",notes = "分页查询告警策略信息")
//	@GetMapping(value = "/alert/strategy")
//	public Pagination<Strategy> getStrategyPagination(@ApiParam(name = "pageNo",value = "页号",required = true,defaultValue = "1") @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
//	                                                @ApiParam(name = "pageSize",value = "页大小",required = true,defaultValue = "10") @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
//		return this.strategyService.getUserV1Pagination(pageNo, pageSize);
//	}

//	@ApiOperation(value = "告警策略信息主页",notes = "告警策略信息主页")
//	@GetMapping(value = "/strategy")
//	public Pagination<Strategy> getStrategyPagination(@ApiParam(name = "pageSize",value = "页大小",required = true,defaultValue = "10") @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
//		return this.strategyService.getUserV1Pagination(pageNo, pageSize);
//	}

	@GetMapping("/test")
	public void testHaveUserIdHeader(@RequestHeader(HttpHeaders.X_LOGIN_USER) Long id) {
		System.out.println(id);
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
