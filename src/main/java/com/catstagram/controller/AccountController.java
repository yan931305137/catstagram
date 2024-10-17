package com.catstagram.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Api(tags = "账户管理")
@Controller
public class AccountController {
	//返回错误页面
	@ApiOperation(value = "处理账户URL")
	@GetMapping({"/catstagram/account/{url}", "/catstagram/{url}/{url2}"})
	public String accountUrl() {
		return "emptyPage";
	}
}