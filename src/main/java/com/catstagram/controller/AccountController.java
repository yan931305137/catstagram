package com.catstagram.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// 账户管理控制器
@Controller
public class AccountController {
	// 返回错误页面
	@GetMapping({"/catstagram/account/{url}", "/catstagram/{url}/{url2}"})
	public String accountUrl() {
		return "emptyPage";
	}
}