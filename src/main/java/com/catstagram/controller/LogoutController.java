package com.catstagram.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "登出管理")
@Controller
public class LogoutController {
	
	@ApiOperation(value = "登出", notes = "用户登出并重定向到登录页面")
	@GetMapping("/catstagram/account/logout")
	public ModelAndView logout(HttpServletRequest req) {
		HttpSession session = req.getSession();
		Integer w_sidx = (Integer)session.getAttribute("sidx");
		ModelAndView mav = new ModelAndView();
		
		if(w_sidx != null) {
			session.removeAttribute("sidx");
			session.removeAttribute("sid");
			session.removeAttribute("sname");
			session.removeAttribute("simg");
			session.removeAttribute("lastTime");
			mav.setViewName("redirect:/catstagram");
		} else {
			mav.addObject("msg", "错误的访问。");
			mav.addObject("goUrl", "/catstagram");
			mav.setViewName("msg/msg");
		}
		return mav;
	}
}