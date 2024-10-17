package com.catstagram.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.catstagram.utils.encryption.Encryption;
import com.catstagram.entity.Member;
import com.catstagram.service.MemberService;

@Controller
public class SignUpController {
	
	@Autowired
	private MemberService memberService;
	
	/**
	 * 跳转到注册页面，如果已登录则提示注销后才能访问
	 * @param session HttpSession对象
	 * @return ModelAndView视图模型
	 */
	@GetMapping("/catstagram/account/signup")
	public ModelAndView signUp(HttpSession session) {
		Integer sidx = (Integer)session.getAttribute("sidx");
		ModelAndView mav = new ModelAndView();
		if(sidx == null) {
			mav.setViewName("signup");
		} else {
			mav.addObject("msg", "注销后才能访问此页面。");
			mav.addObject("goUrl", "/catstagram");
			mav.setViewName("msg/msg");
		}
		return mav;
	}
	
	/**
	 * 通过POST请求检查ID是否重复
	 * @param id 用户ID
	 * @return 检查结果
	 */
	@ResponseBody
	@PostMapping("/catstagram/account/idCheck")
	public String idCheck(@RequestParam("id") String id) {
		String member_id = null;
		try {
			member_id = memberService.idCheck(id);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return member_id;
	}
	
	/**
	 * 通过GET请求访问ID检查时提示非法访问
	 * @param session HttpSession对象
	 * @return ModelAndView视图模型
	 */
	@GetMapping("/catstagram/account/idCheck")
	public ModelAndView idCheckGet(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("msg", "非法访问。");
		mav.addObject("goUrl", "/catstagram");
		mav.setViewName("msg/msg");
		return mav;
	}

	/**
	 * 通过POST请求进行注册，密码会被加密
	 * @param dto 用户信息对象
	 * @return ModelAndView视图模型
	 */
	@PostMapping("/catstagram/account/signup")
	public ModelAndView signup(Member dto) {
		dto.setMember_pwd(Encryption.sha256(dto.getMember_pwd())); // 密码加密
		int result = 0;
		try {
			result = memberService.signup(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}

		ModelAndView mav = new ModelAndView();
		String msg = result>0 ? "🎉 恭喜您注册成功！ 🎉" : "注册失败！请联系管理员。";
		
		mav.addObject("msg", msg);
		mav.addObject("goUrl", "/catstagram");
		mav.setViewName("msg/msg");
		return mav;
	}
}