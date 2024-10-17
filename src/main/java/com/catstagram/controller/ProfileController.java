package com.catstagram.controller;

import java.io.*;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.catstagram.entity.Alarm;
import com.catstagram.entity.Member;
import com.catstagram.service.MemberService;

@Controller
public class ProfileController {
	
	@Autowired
	private MemberService memberService;
	
	/**
	 * 进入个人资料修改页面并加载已保存的个人资料
	 * 根据会话中的sidx加载个人资料
	 */
	@GetMapping("/catstagram/account/profileUpdate")
	public ModelAndView profileUpdateForm(HttpSession session) {
		Integer w_sidx = (Integer)session.getAttribute("sidx");
		ModelAndView mav = new ModelAndView();
		
		if(w_sidx != null) {
			int sidx = (Integer)session.getAttribute("sidx");
			String content = null;
			try {
				content = memberService.profileInfo(sidx);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
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
			mav.addObject("member_content", content);
			mav.setViewName("profileUpdate");
		} else {
			mav.addObject("msg", "错误的访问。");
			mav.addObject("goUrl", "/catstagram");
			mav.setViewName("msg/msg");	
		}
		return mav;
	}
	
	/**
	 * 修改个人资料
	 * 根据传入的Member对象更新个人资料
	 */
	@PostMapping("/catstagram/account/profileUpdate")
	public String profileUpdate(Model model, Member dto, HttpSession session) {
		int sidx = (Integer)session.getAttribute("sidx");
		dto.setMember_idx(sidx);
		int result = 0;
		try {
			result = memberService.profileUpdate(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String msg = result>0 ? "个人资料已修改。" : "个人资料修改失败！";
		model.addAttribute("msg", msg);
		model.addAttribute("goUrl", "/catstagram/account/profileUpdate");
		return "msg/msg";
	}
	
	/**
	 * 打开个人资料图片更改弹出窗口
	 * 根据会话中的sidx打开图片更改窗口
	 */
	@GetMapping("/catstagram/account/profileImgPopup")
	public ModelAndView profileImgPopup(HttpSession session) {
		Integer w_sidx = (Integer)session.getAttribute("sidx");
		ModelAndView mav = new ModelAndView();
		
		if(w_sidx == null) {
			mav.addObject("msg", "错误的访问。");
			mav.addObject("goUrl", "/catstagram");
			mav.setViewName("msg/msg");
		} else {
			mav.setViewName("profileImgPopup");
		}
		return mav;
	}
	
	/**
	 * 修改个人资料图片
	 * 根据上传的图片文件更新个人资料图片
	 */
	@PostMapping("/catstagram/account/profileImgUpdate")
	public ModelAndView profileImgUpdate(HttpSession session, MultipartHttpServletRequest req) {
		MultipartFile upl = req.getFile("member_img");
		String upload = upl.getOriginalFilename();
		
		int member_idx = (Integer)session.getAttribute("sidx");
		Member dto = new Member();
		dto.setMember_idx(member_idx);
		
		ModelAndView mav = new ModelAndView();
		
		if(!upload.equals("")) {
			String noExt = upload.substring(0, upload.lastIndexOf("."));
			String ext = upload.substring(upload.lastIndexOf(".") + 1);
			String savePath = "src/main/resources/static/upload/member/";
			String saveFileName = "";
			
			try {
				byte bytes[] = upl.getBytes();
				String filefull = savePath + upload;
				File f = new File(filefull);
				if(f.isFile()) {
					boolean ex = true;
					int index = 0;
					while(ex) {
						index++;
						saveFileName = noExt+"("+index+")."+ext;
						String dictFile = savePath + saveFileName;
						ex = new File(dictFile).isFile();
						f = new File(dictFile);
					}
				} else if(!f.isFile()) {
					saveFileName = upload;
				}
				
				FileOutputStream fos = new FileOutputStream(f);
				fos.write(bytes);
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			dto.setMember_img(saveFileName);
			session.setAttribute("simg", saveFileName);
		} else {
			dto.setMember_img("");
			session.setAttribute("simg", "");
		}
		
		int result = 0;
		try {
			result = memberService.profileImgUpdate(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String msg = result>0? "个人资料图片已修改。" : "个人资料图片修改失败！";
		mav.addObject("msg", msg);
		mav.setViewName("msg/msgPopup");
		
		return mav;
	}
	
	/**
	 * 错误的访问
	 * 处理错误的访问请求
	 */
	@GetMapping("/catstagram/account/profileImgUpdate")
	public ModelAndView profileImgUpdateGet() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("msg", "错误的访问。");
		mav.addObject("goUrl", "/catstagram");
		mav.setViewName("msg/msg");
		
		return mav;
	}
}