package com.catstagram.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LogoutController {
	
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