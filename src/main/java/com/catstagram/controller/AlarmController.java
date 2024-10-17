package com.catstagram.controller;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.catstagram.entity.Alarm;
import com.catstagram.service.MemberService;

@Controller
public class AlarmController {
	
	@Autowired
	private MemberService memberService;

	// 将我看到的最后一个警报的时间存储在会话中
	@ResponseBody
	@PostMapping("/catstagram/account/lastAlarmTimeSave")
	public String lastAlarmTimeSave(@RequestParam("lastAlarmTime") String lastAlarmTime, HttpSession session) {
		int sidx = (Integer)session.getAttribute("sidx");
		
		if(lastAlarmTime != null && !lastAlarmTime.isEmpty()) {
			try {
				SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
				Date utilDate = dateFormat.parse(lastAlarmTime);
					        
		        Timestamp timestampDate = new Timestamp(utilDate.getTime());
				
				memberService.lastAlarmSave(timestampDate, sidx);
				session.setAttribute("lastTime", timestampDate);	
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "success"; // 没有特别的响应
	}
	
	// 是否有新的警报
	@ResponseBody
	@PostMapping("/catstagram/account/lastAlarmTime")
	public int lastAlarmTime(@RequestParam("lastAlarmTime") String lastAlarmTime, HttpSession session) {
		int sidx = (Integer)session.getAttribute("sidx");
		Date lastTime = (Date)session.getAttribute("lastTime");

		SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        Date utilDate = null;
		try {
			utilDate = dateFormat.parse(lastAlarmTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		List<Alarm> alarmList = null;
		try {
			alarmList = memberService.alarmList(sidx);
		} catch (Exception e) {
			e.printStackTrace();
		}
        
		int result = 0;
		if(utilDate != null) {
			Timestamp timestampDate = new Timestamp(utilDate.getTime());
			if(lastTime != null && timestampDate != null) {
				if(lastTime.before(timestampDate)) {
					// 显示红色圆圈
					result = 1;
				} else {
					// 不显示红色圆圈
					result = 0;
				}
			} else if(alarmList != null) {
				if(alarmList.size() == 1) {
					result = 1;
				}
			}
		}
		return result;
	}
}