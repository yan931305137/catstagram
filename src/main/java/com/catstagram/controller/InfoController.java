package com.catstagram.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.catstagram.utils.encryption.Encryption;
import com.catstagram.entity.Alarm;
import com.catstagram.entity.Member;
import com.catstagram.service.MemberService;

@Controller
public class InfoController {
	
	@Autowired
	private MemberService memberService;
	
	@GetMapping("catstagram/account/infoUpdate")
	public ModelAndView infoUpdateForm(HttpSession session) {
		Integer w_sidx = (Integer)session.getAttribute("sidx");
		ModelAndView mav = new ModelAndView();
		int sidx = (Integer)session.getAttribute("sidx");
		
		if(w_sidx == null) {
			mav.addObject("msg", "错误的访问。");
			mav.addObject("goUrl", "/catstagram");
			mav.setViewName("msg/msg");
		} else {
			// Header的通知列表
			List<Alarm> alarmList = null;
			try {
				alarmList = memberService.alarmList(sidx);
				for(int i=0; i<alarmList.size(); i++) {
					// 通知时间少于1小时
					if(alarmList.get(i).getAlarm_date_minute() < 60) {
						alarmList.get(i).setAlarm_date_string(alarmList.get(i).getAlarm_date_minute()+"分钟");
					// 通知时间少于24小时
					} else if(alarmList.get(i).getAlarm_date_minute() >= 60 && alarmList.get(i).getAlarm_date_minute() < 1440) {
						alarmList.get(i).setAlarm_date_string((int)Math.floor(alarmList.get(i).getAlarm_date_minute()/60)+"小时");
					// 通知时间超过24小时
					} else if(alarmList.get(i).getAlarm_date_minute() >= 1440 && alarmList.get(i).getAlarm_date_minute() < 10080) {
						alarmList.get(i).setAlarm_date_string((int)Math.floor(alarmList.get(i).getAlarm_date_minute()/1440)+"天");
					// 通知时间超过7天
					} else if(alarmList.get(i).getAlarm_date_minute() >= 10080) {
						alarmList.get(i).setAlarm_date_string((int)Math.floor(alarmList.get(i).getAlarm_date_minute()/10080)+"周");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			mav.addObject("alarmList", alarmList);
			mav.setViewName("infoUpdate");
		}
		return mav;
	}
	
	@PostMapping("/catstagram/account/infoUpdate")
	public ModelAndView infoUpdate(
			@RequestParam("member_id") String member_id,
			@RequestParam("member_name") String member_name,
			@RequestParam("member_email") String member_email,
			HttpSession session)
	{
		int sidx = (Integer)session.getAttribute("sidx");
		
		Member dto = new Member();
		dto.setMember_idx(sidx);
		dto.setMember_id(member_id);
		dto.setMember_name(member_name);
		dto.setMember_email(member_email);

		int result = 0;
		try {
			result = memberService.infoUpdate(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ModelAndView mav = new ModelAndView();
		if(result>0) {
			mav.addObject("msg", "会员信息已修改。");
			session.setAttribute("sid", dto.getMember_id());
			session.setAttribute("sname", dto.getMember_name());
			mav.setViewName("msg/msg");			
		} else {
			mav.addObject("msg", "会员信息修改失败！");
			mav.setViewName("msg/msg");
		}
		return mav;
	}
	
	@GetMapping("/catstagram/account/pwdUpdate")
	public ModelAndView pwdChkFormForPwdUpdate(@RequestParam(value = "page", defaultValue = "") String page,
								   HttpSession session) {
		Integer sidx = (Integer)session.getAttribute("sidx");
		ModelAndView mav = new ModelAndView();
		
		if(!page.equals("") && page.equals("p") && page != null && sidx != null) {
			// Header的通知列表
			List<Alarm> alarmList = null;
			try {
				alarmList = memberService.alarmList(sidx);
				for(int i=0; i<alarmList.size(); i++) {
					// 通知时间少于1小时
					if(alarmList.get(i).getAlarm_date_minute() < 60) {
						alarmList.get(i).setAlarm_date_string(alarmList.get(i).getAlarm_date_minute()+"分钟");
					// 通知时间少于24小时
					} else if(alarmList.get(i).getAlarm_date_minute() >= 60 && alarmList.get(i).getAlarm_date_minute() < 1440) {
						alarmList.get(i).setAlarm_date_string((int)Math.floor(alarmList.get(i).getAlarm_date_minute()/60)+"小时");
					// 通知时间超过24小时
					} else if(alarmList.get(i).getAlarm_date_minute() >= 1440 && alarmList.get(i).getAlarm_date_minute() < 10080) {
						alarmList.get(i).setAlarm_date_string((int)Math.floor(alarmList.get(i).getAlarm_date_minute()/1440)+"天");
					// 通知时间超过7天
					} else if(alarmList.get(i).getAlarm_date_minute() >= 10080) {
						alarmList.get(i).setAlarm_date_string((int)Math.floor(alarmList.get(i).getAlarm_date_minute()/10080)+"周");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			mav.addObject("alarmList", alarmList);
			mav.addObject("page", page);
			mav.setViewName("pwdChk");
		} else if(page.equals("") || page == null || !page.equals("p") || sidx == null) {
			mav.addObject("msg", "错误的访问。");
			mav.addObject("goUrl", "/catstagram"); 
			mav.setViewName("msg/msg");
		}

		return mav;
	}
	
	@GetMapping("/catstagram/account/quit")
	public ModelAndView pwdChkFormForQuit(@RequestParam(value = "page", defaultValue = "") String page,
								   HttpSession session) {
		Integer sidx = (Integer)session.getAttribute("sidx");
		ModelAndView mav = new ModelAndView();
		
		if(!page.equals("") && page.equals("q") && page != null && sidx != null) {
			// Header的通知列表
			List<Alarm> alarmList = null;
			try {
				alarmList = memberService.alarmList(sidx);
				for(int i=0; i<alarmList.size(); i++) {
					// 通知时间少于1小时
					if(alarmList.get(i).getAlarm_date_minute() < 60) {
						alarmList.get(i).setAlarm_date_string(alarmList.get(i).getAlarm_date_minute()+"分钟");
					// 通知时间少于24小时
					} else if(alarmList.get(i).getAlarm_date_minute() >= 60 && alarmList.get(i).getAlarm_date_minute() < 1440) {
						alarmList.get(i).setAlarm_date_string((int)Math.floor(alarmList.get(i).getAlarm_date_minute()/60)+"小时");
					// 通知时间超过24小时
					} else if(alarmList.get(i).getAlarm_date_minute() >= 1440 && alarmList.get(i).getAlarm_date_minute() < 10080) {
						alarmList.get(i).setAlarm_date_string((int)Math.floor(alarmList.get(i).getAlarm_date_minute()/1440)+"天");
					// 通知时间超过7天
					} else if(alarmList.get(i).getAlarm_date_minute() >= 10080) {
						alarmList.get(i).setAlarm_date_string((int)Math.floor(alarmList.get(i).getAlarm_date_minute()/10080)+"周");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			mav.addObject("alarmList", alarmList);
			mav.addObject("page", page);
			mav.setViewName("pwdChk");
		} else if(page.equals("") || page == null || !page.equals("q") || sidx == null) {
			mav.addObject("goUrl", "/catstagram");
			mav.addObject("msg", "错误的访问。");
			mav.setViewName("msg/msg");
		}

		return mav;
	}
	
	@PostMapping({"/catstagram/account/pwdUpdate", "/catstagram/account/quit"})
	public ModelAndView pwdChk(@RequestParam(value = "page", defaultValue = "") String page,
						 	   @RequestParam("member_pwd") String member_pwd, HttpSession session) {
		Integer sidx = (Integer)session.getAttribute("sidx");
		ModelAndView mav = new ModelAndView();
		String dbPwd = null;
		try {
			dbPwd = memberService.pwdChk(sidx);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(dbPwd.equals(Encryption.sha256(member_pwd))) {
			if(!page.equals("") && page != null && sidx != null) {
				// Header的通知列表
				List<Alarm> alarmList = null;
				try {
					alarmList = memberService.alarmList(sidx);
					for(int i=0; i<alarmList.size(); i++) {
						// 通知时间少于1小时
						if(alarmList.get(i).getAlarm_date_minute() < 60) {
							alarmList.get(i).setAlarm_date_string(alarmList.get(i).getAlarm_date_minute()+"分钟");
						// 通知时间少于24小时
						} else if(alarmList.get(i).getAlarm_date_minute() >= 60 && alarmList.get(i).getAlarm_date_minute() < 1440) {
							alarmList.get(i).setAlarm_date_string((int)Math.floor(alarmList.get(i).getAlarm_date_minute()/60)+"小时");
						// 通知时间超过24小时
						} else if(alarmList.get(i).getAlarm_date_minute() >= 1440 && alarmList.get(i).getAlarm_date_minute() < 10080) {
							alarmList.get(i).setAlarm_date_string((int)Math.floor(alarmList.get(i).getAlarm_date_minute()/1440)+"天");
						// 通知时间超过7天
						} else if(alarmList.get(i).getAlarm_date_minute() >= 10080) {
							alarmList.get(i).setAlarm_date_string((int)Math.floor(alarmList.get(i).getAlarm_date_minute()/10080)+"周");
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				mav.addObject("alarmList", alarmList);
				
				if(page.equals("p")) {
					mav.setViewName("pwdUpdate");
				} else if(page.equals("q")) {
					mav.setViewName("quit");
				}
			} else if(page.equals("") || page == null) {
				mav.addObject("goUrl", "/catstagram");
				mav.addObject("msg", "错误的访问。");
				mav.setViewName("msg/msg");
			}
		} else {
			mav.addObject("msg", "请确认密码。");
			mav.setViewName("msg/msgNoDeco");
		}
		return mav;
	}
	
	@PostMapping("/catstagram/account/pwdUpdateOk")
	public ModelAndView pwdUpdateOk(@RequestParam("member_pwd") String pwd, HttpSession session) {
		int sidx = (Integer)session.getAttribute("sidx");
		Member dto = new Member();
		dto.setMember_pwd(Encryption.sha256(pwd));
		dto.setMember_idx(sidx);
		int result = 0;
		try {
			result = memberService.pwdUpdateOk(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String msg = result>0 ? "密码已修改。" : "密码修改失败！";
		ModelAndView mav = new ModelAndView();
		mav.addObject("msg", msg);
		mav.addObject("goUrl", "/catstagram");
		mav.setViewName("msg/msg");
		return mav;
	}
	
	@GetMapping("/catstagram/account/pwdUpdateOk")
	public ModelAndView pwdUpdateOkGet() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("msg", "错误的访问。");
		mav.addObject("goUrl", "/catstagram");
		mav.setViewName("msg/msg");
		return mav;
	}
	
	@PostMapping("/catstagram/account/quitOk")
	public ModelAndView quitOk(HttpSession session, HttpServletResponse resp) {
		int sidx = (Integer)session.getAttribute("sidx");
		String sid = (String)session.getAttribute("sid");
		
		int result = 0;
		try {
			result = memberService.quit(sidx);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ModelAndView mav = new ModelAndView();
		String msg = null;
		if(result>0) {
			msg = "再见。 😭";
			
			mav.addObject("msg", msg);
			mav.addObject("goUrl", "/catstagram");
			mav.setViewName("msg/msg");
			
			// 删除保存ID的Cookie
			Cookie ck = new Cookie("saveid", sid);
			ck.setMaxAge(0);
			ck.setPath("/");
			resp.addCookie(ck);
			
			// 删除Session
			session.removeAttribute("sidx");
			session.removeAttribute("sid");
			session.removeAttribute("sname");
			session.removeAttribute("simg");
		} else {
			msg = "会员退出失败！";
			mav.addObject("msg", msg);
			mav.addObject("goUrl", "/catstagram");
			mav.setViewName("msg/msg");
		}
		return mav;
	}
	
	@GetMapping("/catstagram/account/quitOk")
	public ModelAndView quitOkGet() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("msg", "错误的访问。");
		mav.addObject("goUrl", "/catstagram");
		mav.setViewName("msg/msg");
		return mav;
	}
}