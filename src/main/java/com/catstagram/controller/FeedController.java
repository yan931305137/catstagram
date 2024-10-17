package com.catstagram.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.catstagram.entity.Alarm;
import com.catstagram.entity.Feed;
import com.catstagram.service.FeedService;
import com.catstagram.entity.FeedLike;
import com.catstagram.service.FeedLikeService;
import com.catstagram.service.MemberService;

@Controller
public class FeedController {
	
	@Autowired
	private FeedService feedService;
	
	@Autowired
	private FeedLikeService feedLikeService;
	
	@Autowired
	private MemberService memberService;
	
	@GetMapping("/catstagram/account/feedWrite")
	public ModelAndView feedWriteForm(HttpSession session) {
		Integer w_sidx = (Integer)session.getAttribute("sidx");
		ModelAndView mav = new ModelAndView();
		int sidx = (Integer)session.getAttribute("sidx");
		
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
		
		if(w_sidx == null) {
			mav.addObject("msg", "非法访问。");
			mav.addObject("goUrl", "/catstagram");
			mav.setViewName("msg/msg");
		} else {
			mav.addObject("alarmList", alarmList);
			mav.setViewName("feedWrite");
		}
		return mav;
	}
	
	@PostMapping("/catstagram/account/feedWrite")
	public ModelAndView feedWrite(HttpSession session, MultipartHttpServletRequest req) {
		MultipartFile upl = req.getFile("feed_img");
		String upload = upl.getOriginalFilename();
		String noExt = upload.substring(0, upload.lastIndexOf("."));
		String ext = upload.substring(upload.lastIndexOf(".") + 1);
		
		String savePath = "src/main/resources/static/upload/feed/";
		
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
		}  catch (IOException e) {
			e.printStackTrace();
		}
		
		int member_idx = (Integer)session.getAttribute("sidx");
		Feed dto = new Feed();
		dto.setMember_idx(member_idx);
		dto.setFeed_img(saveFileName);
		dto.setFeed_content(req.getParameter("feed_content"));
		
		int result = 0;
		try {
			result = feedService.feedWrite(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ModelAndView mav = new ModelAndView();
		String msg = result>0 ? "动态发布成功！" : "动态发布失败！";
		
		mav.addObject("msg", msg);
		mav.addObject("goUrl", "/catstagram");
		mav.setViewName("msg/msg");
		return mav;
	}
	
	@GetMapping("/catstagram/account/feedUpdate")
	public ModelAndView feedUpdateForm(@RequestParam(value = "feed_idx", defaultValue = "0") int feed_idx, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		Integer sidx = (Integer)session.getAttribute("sidx");
		Integer feed_idx_c = (Integer)feed_idx;
		
		// 该动态作者的idx
		Integer member_idx = 0;
		try {
			member_idx = feedService.feedMemberIdx(feed_idx_c);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 要修改的动态信息
		Feed dto = new Feed();
		try {
			dto = feedService.feedUpdateInfo(feed_idx);
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

		if(feed_idx_c != null && member_idx.equals(sidx)) {
			mav.addObject("alarmList", alarmList);
			mav.addObject("feedInfo", dto);
			mav.setViewName("feedUpdate");
		} else if(feed_idx_c == null || !member_idx.equals(sidx) || sidx == null) {
			mav.addObject("msg", "非法访问。");
			mav.addObject("goUrl", "/catstagram");
			mav.setViewName("msg/msg");
		}
		
		return mav;
	}
	
	@PostMapping("/catstagram/account/feedUpdate")
	public ModelAndView feedUpdate(Feed dto) {
		int result = 0;
		try {
			result = feedService.feedUpdate(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ModelAndView mav = new ModelAndView();
		String msg = result>0 ? "动态修改成功。" : "动态修改失败！";
		mav.addObject("msg", msg);
		mav.addObject("goUrl", "/catstagram");
		mav.setViewName("msg/msg");
		
		return mav;
	}
	
	@PostMapping("/catstagram/account/feedDel")
	public ModelAndView feedDel(@RequestParam("feed_idx") int feed_idx) {
		int result = 0;
		try {
			result = feedService.feedDel(feed_idx);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ModelAndView mav = new ModelAndView();
		String msg = result>0 ? "动态已删除。" : "动态删除失败！";
		mav.addObject("msg", msg);
		mav.addObject("goUrl", "/catstagram");
		mav.setViewName("msg/msg");
		return mav;
	}

	@GetMapping("/catstagram/account/feedDel")
	public ModelAndView feedDelGet() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("msg", "非法访问。");
		mav.addObject("goUrl", "/catstagram");
		mav.setViewName("msg/msg");
		
		return mav;
	}
	
	@ResponseBody
	@PostMapping("/catstagram/account/likeFeed")
	public int likeFeed(@RequestParam("feed_idx") int feed_idx, HttpSession session) {
		int sidx = (Integer)session.getAttribute("sidx");
		FeedLike dto = new FeedLike();
		dto.setFeed_idx(feed_idx);
		dto.setMember_idx(sidx);
		
		int feedLikeCount = 0;
		try {
			feedLikeService.likeFeed(dto);
			feedService.feedLikeCountPlus(feed_idx);
			feedLikeCount = feedService.feedLikeCount(feed_idx);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return feedLikeCount;
	}
	
	@ResponseBody
	@PostMapping("/catstagram/account/likeFeedCancel")
	public int likeFeedCancel(@RequestParam("feed_idx") int feed_idx, HttpSession session) {
		int sidx = (Integer)session.getAttribute("sidx");
		FeedLike dto = new FeedLike();
		dto.setFeed_idx(feed_idx);
		dto.setMember_idx(sidx);
		
		int feedLikeCount = 0;
		try {
			feedLikeService.likeFeedCancel(dto);
			feedService.feedLikeCountMinus(feed_idx);
			feedLikeCount = feedService.feedLikeCount(feed_idx);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return feedLikeCount;
	}
}