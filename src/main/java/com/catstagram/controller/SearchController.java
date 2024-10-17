package com.catstagram.controller;

import javax.servlet.http.HttpSession;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.catstagram.entity.Alarm;
import com.catstagram.entity.Member;
import com.catstagram.service.MemberService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Controller
@Api(tags = "搜索控制器")
public class SearchController {
	
	@Autowired
	private MemberService memberService;
	
	@ApiOperation(value = "通过会员ID搜索", notes = "根据提供的会员ID进行搜索")
	@GetMapping("/catstagram/account/search")
	public ModelAndView search(
			@ApiParam(value = "搜索的会员ID", required = false) @RequestParam(value = "id", defaultValue = "") String search_id, 
			HttpSession session) {
		String sid = (String)session.getAttribute("sid");
		Integer sidx = (Integer)session.getAttribute("sidx");
		ModelAndView mav = new ModelAndView();
		
		if(!search_id.equals("") && search_id != null && sidx != null && sid != null) {
			List<Member> list = null;
			try {
				list = memberService.searchList(search_id, sid, sidx);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			// Header的通知列表
			List<Alarm> alarmList = null;
			try {
				alarmList = memberService.alarmList(sidx);
				for(int i=0; i<alarmList.size(); i++) {
					// 通知生成时间少于1小时
					if(alarmList.get(i).getAlarm_date_minute() < 60) {
						alarmList.get(i).setAlarm_date_string(alarmList.get(i).getAlarm_date_minute()+"分钟");
					// 通知生成时间少于24小时
					} else if(alarmList.get(i).getAlarm_date_minute() >= 60 && alarmList.get(i).getAlarm_date_minute() < 1440) {
						alarmList.get(i).setAlarm_date_string((int)Math.floor(alarmList.get(i).getAlarm_date_minute()/60)+"小时");
					// 通知生成时间少于7天
					} else if(alarmList.get(i).getAlarm_date_minute() >= 1440 && alarmList.get(i).getAlarm_date_minute() < 10080) {
						alarmList.get(i).setAlarm_date_string((int)Math.floor(alarmList.get(i).getAlarm_date_minute()/1440)+"天");
					// 通知生成时间超过7天
					} else if(alarmList.get(i).getAlarm_date_minute() >= 10080) {
						alarmList.get(i).setAlarm_date_string((int)Math.floor(alarmList.get(i).getAlarm_date_minute()/10080)+"周");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			mav.addObject("alarmList", alarmList);
			mav.addObject("list", list);
			mav.addObject("search_id", search_id);
			mav.setViewName("search");
		} else if(search_id.equals("") || search_id == null || sidx == null || sid == null) {
			mav.addObject("goUrl", "/catstagram"); 
			mav.addObject("msg", "错误的访问。");
			mav.setViewName("msg/msg");	
		}
		
		return mav;
	}
}