package com.catstagram.controller;

import java.text.DecimalFormat;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import com.catstagram.entity.Alarm;
import com.catstagram.entity.MainFollowingFeed;
import com.catstagram.service.FeedService;
import com.catstagram.entity.Follow;
import com.catstagram.service.FollowService;
import com.catstagram.entity.Member;
import com.catstagram.service.MemberService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Catstagram管理")
@Controller
public class CatstagramController {
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private FeedService feedService;
	
	@Autowired
	private FollowService followService;
	
	@ApiOperation(value = "获取Catstagram页面")
	@GetMapping("/catstagram/{member_id}")
	public ModelAndView catstagram(@PathVariable String member_id, HttpSession session) {
		Integer w_sidx = (Integer)session.getAttribute("sidx");
		int sidx = (Integer)session.getAttribute("sidx");
		ModelAndView mav = new ModelAndView();
		DecimalFormat df = new DecimalFormat("#,#");
		
		Member dto = null;
		//动态用户列表
		List<MainFollowingFeed> feedList = null;
		//关注列表
		Follow whoFollow = null;
		String[] followingList = null;
		
		try {
			// catstagram 上端基本信息
			dto = memberService.urlIdxIdSelect(member_id, sidx);
			if(dto != null) {
				if(dto.getMember_intro() != null) {
					dto.setMember_intro(dto.getMember_intro().replace("\n", "<br>"));
				}
				
				// 帖子数, 关注者数, 关注数 1,000 以上 K, 1,000,000以上 M并且只显示小数点后一位
				if(dto.getFeed_count() < 1000) {
					dto.setFeed_count_KM(dto.getFeed_count()+"");
				} else if(dto.getFeed_count() >= 1000 && dto.getFeed_count() <= 999999) {
					dto.setFeed_count_KM(df.format(dto.getFeed_count())+"K");
				} else if(dto.getFeed_count() > 1000000) {
					dto.setFeed_count_KM(df.format(dto.getFeed_count())+"M");
				}
				
				if(dto.getFollower_count() < 1000) {
					dto.setFollower_count_KM(dto.getFollower_count()+"");
				} else if(dto.getFollower_count() >= 1000 && dto.getFollower_count() <= 999999) {
					dto.setFollower_count_KM(df.format(dto.getFollower_count())+"K");
				} else if(dto.getFollower_count() > 1000000) {
					dto.setFollower_count_KM(df.format(dto.getFollower_count())+"M");
				}
				
				if(dto.getFollowing_count() < 1000) {
					dto.setFollowing_count_KM(dto.getFollowing_count()+"");
				} else if(dto.getFollowing_count() >= 1000 && dto.getFollowing_count() <= 999999) {
					dto.setFollowing_count_KM(df.format(dto.getFollowing_count())+"K");
				} else if(dto.getFollowing_count() > 1000000) {
					dto.setFollowing_count_KM(df.format(dto.getFollowing_count())+"M");
				}
				
				// 动态列表
				feedList = feedService.feedList(dto.getMember_idx(), sidx);
					for(int i=0; i<feedList.size(); i++) {
						if(feedList.get(i) != null) {
						// 动态发布不到1小时
						if(feedList.get(i).getFeed_date_minute() < 60) {
							feedList.get(i).setFeed_date_time(feedList.get(i).getFeed_date_minute()+"分");
							// 动态发布不到24小时
						} else if(feedList.get(i).getFeed_date_minute() >= 60 && feedList.get(i).getFeed_date_minute() < 1440) {
							feedList.get(i).setFeed_date_time((int)Math.floor(feedList.get(i).getFeed_date_minute()/60)+"小时");
							// 动态发布超过24小时
						} else if(feedList.get(i).getFeed_date_minute() >= 1440 && feedList.get(i).getFeed_date_minute() < 10080) {
							feedList.get(i).setFeed_date_time((int)Math.floor(feedList.get(i).getFeed_date_minute()/1440)+"天");
							// 动态发布超过7天
						} else if(feedList.get(i).getFeed_date_minute() >= 10080) {
							feedList.get(i).setFeed_date_time((int)Math.floor(feedList.get(i).getFeed_date_minute()/10080)+"周");
						}
						// 动态内容换行处理
						feedList.get(i).setFeed_content(feedList.get(i).getFeed_content().replaceAll("\n", "<br>"));
					}
				}

				// 该会员是我的关注中谁关注了
				whoFollow = followService.whoFollow(dto.getMember_idx(), sidx);
				if(whoFollow != null) {
					if(whoFollow.getNum_of_followers() == -1) {
						whoFollow.setNum_of_followers(0);
					}
					if(whoFollow.getMy_following_list() != null) {
						followingList = whoFollow.getMy_following_list().split(",");
						whoFollow.setMy_following_list_arr(followingList);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Header的通知列表
		List<Alarm> alarmList = null;
		try {
			alarmList = memberService.alarmList(sidx);
			for(int i=0; i<alarmList.size(); i++) {
				// 通知不到1小时
				if(alarmList.get(i).getAlarm_date_minute() < 60) {
					alarmList.get(i).setAlarm_date_string(alarmList.get(i).getAlarm_date_minute()+"分");
				// 通知不到24小时
				} else if(alarmList.get(i).getAlarm_date_minute() >= 60 && alarmList.get(i).getAlarm_date_minute() < 1440) {
					alarmList.get(i).setAlarm_date_string((int)Math.floor(alarmList.get(i).getAlarm_date_minute()/60)+"小时");
				// 通知超过24小时
				} else if(alarmList.get(i).getAlarm_date_minute() >= 1440 && alarmList.get(i).getAlarm_date_minute() < 10080) {
					alarmList.get(i).setAlarm_date_string((int)Math.floor(alarmList.get(i).getAlarm_date_minute()/1440)+"天");
				// 通知超过7天
				} else if(alarmList.get(i).getAlarm_date_minute() >= 10080) {
					alarmList.get(i).setAlarm_date_string((int)Math.floor(alarmList.get(i).getAlarm_date_minute()/10080)+"周");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(dto != null && w_sidx != null) {
			mav.addObject("dto", dto);
			mav.addObject("whoFollow", whoFollow);
			mav.addObject("feedList", feedList);
			mav.addObject("alarmList", alarmList);
			mav.setViewName("catstagram");
		} else if(dto == null) {
			mav.setViewName("emptyPage");
		} else if(w_sidx == null) {
			mav.addObject("msg", "登录后使用。");
			mav.addObject("goUrl", "/catstagram");
			mav.setViewName("msg/msg");
		}
		
		return mav;
	}
}