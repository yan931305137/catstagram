package com.catstagram.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
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
import com.catstagram.entity.MainFollowingFeed;
import com.catstagram.service.FeedService;
import com.catstagram.entity.Follow;
import com.catstagram.service.FollowService;
import com.catstagram.entity.Member;
import com.catstagram.service.MemberService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "主页管理")
@Controller
public class IndexController {

	@Autowired
	private MemberService memberService;

	@Autowired
	private FollowService followService;

	@Autowired
	private FeedService feedService;

	@ApiOperation(value = "进入登录页面", notes = "跳转到登录页面")
	@GetMapping("/catstagram")
	public ModelAndView index(HttpSession session) {
		Integer w_sidx = (Integer)session.getAttribute("sidx"); // 从 session 中获取用户的 idx
		ModelAndView mav = new ModelAndView();
		if (w_sidx == null) {
			mav.setViewName("index"); // 如果用户未登录，跳转到登录页面
		} else {
			int sidx = (Integer)session.getAttribute("sidx");

			// 获取用户关注的人的动态列表
			List<MainFollowingFeed> mainFollowingFeed = null;
			try {
				mainFollowingFeed = feedService.mainFollowingFeed(sidx);
				for (int i = 0; i < mainFollowingFeed.size(); i++) {
					// 动态发布后经过的时间格式化为“分钟/小时/天/周”
					if (mainFollowingFeed.get(i).getFeed_date_minute() < 60) {
						mainFollowingFeed.get(i).setFeed_date_time(mainFollowingFeed.get(i).getFeed_date_minute() + "分钟");
					} else if (mainFollowingFeed.get(i).getFeed_date_minute() < 1440) {
						mainFollowingFeed.get(i).setFeed_date_time((int)Math.floor(mainFollowingFeed.get(i).getFeed_date_minute() / 60) + "小时");
					} else if (mainFollowingFeed.get(i).getFeed_date_minute() < 10080) {
						mainFollowingFeed.get(i).setFeed_date_time((int)Math.floor(mainFollowingFeed.get(i).getFeed_date_minute() / 1440) + "天");
					} else {
						mainFollowingFeed.get(i).setFeed_date_time((int)Math.floor(mainFollowingFeed.get(i).getFeed_date_minute() / 10080) + "周");
					}

					// 将动态内容的换行符转换为 HTML 的 <br> 标签
					mainFollowingFeed.get(i).setFeed_content(mainFollowingFeed.get(i).getFeed_content().replaceAll("\n", "<br>"));
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			// 为用户推荐好友
			List<Follow> suggestedFollowersInMain = null;
			String[] followingList = null;
			try {
				suggestedFollowersInMain = followService.suggestedFollowersInMain(sidx);
				for (int i = 0; i < suggestedFollowersInMain.size(); i++) {
					followingList = suggestedFollowersInMain.get(i).getMy_following_list().split(",");
					suggestedFollowersInMain.get(i).setMy_following_list_arr(followingList);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			// 获取头部的通知列表
			List<Alarm> alarmList = null;
			try {
				alarmList = memberService.alarmList(sidx);
				for (int i = 0; i < alarmList.size(); i++) {
					// 根据通知时间显示时间单位
					if (alarmList.get(i).getAlarm_date_minute() < 60) {
						alarmList.get(i).setAlarm_date_string(alarmList.get(i).getAlarm_date_minute() + "分钟");
					} else if (alarmList.get(i).getAlarm_date_minute() < 1440) {
						alarmList.get(i).setAlarm_date_string((int)Math.floor(alarmList.get(i).getAlarm_date_minute() / 60) + "小时");
					} else if (alarmList.get(i).getAlarm_date_minute() < 10080) {
						alarmList.get(i).setAlarm_date_string((int)Math.floor(alarmList.get(i).getAlarm_date_minute() / 1440) + "天");
					} else {
						alarmList.get(i).setAlarm_date_string((int)Math.floor(alarmList.get(i).getAlarm_date_minute() / 10080) + "周");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			// 将获取到的数据添加到 ModelAndView 对象中
			mav.addObject("alarmList", alarmList);
			mav.addObject("mainFollowingFeed", mainFollowingFeed);
			mav.addObject("suggestedFollowersInMain", suggestedFollowersInMain);
			mav.setViewName("main"); // 如果用户已登录，跳转到主页面
		}

		return mav;
	}

	@ApiOperation(value = "登录功能", notes = "用户登录")
	@PostMapping("/catstagram/account/login")
	public ModelAndView login(@ApiParam(value = "用户ID", required = true) @RequestParam("member_id") String id,
							  @ApiParam(value = "用户密码", required = true) @RequestParam("member_pwd") String pwd,
							  @ApiParam(value = "保存ID选项", required = false) @RequestParam(value = "saveidChk", required = false) String saveid,
							  HttpServletResponse resp,
							  HttpSession session) {
		// 密码加密
		String EncryptPwd = Encryption.sha256(pwd);
		int result = 0;
		try {
			result = memberService.login(id, EncryptPwd);
		} catch (Exception e) {
			e.printStackTrace();
		}

		ModelAndView mav = new ModelAndView();
		if (result == memberService.LOGIN_OK) {
			// 如果勾选了“保存ID”，则创建保存ID的Cookie
			if (saveid == null) {
				Cookie ck = new Cookie("saveid", id);
				ck.setMaxAge(0);
				ck.setPath("/");
				resp.addCookie(ck);
			} else {
				Cookie ck = new Cookie("saveid", id);
				ck.setMaxAge(60 * 60 * 24 * 30); // 设置Cookie的有效期为30天
				ck.setPath("/");
				resp.addCookie(ck);
			}

			// 创建 session
			Member dto = new Member();
			try {
				dto = memberService.sessionInfo(id);
			} catch (Exception e) {
				e.printStackTrace();
			}
			session.setAttribute("sidx", dto.getMember_idx());
			session.setAttribute("sid", dto.getMember_id());
			session.setAttribute("sname", dto.getMember_name());
			session.setAttribute("simg", dto.getMember_img());

			// 获取用户的最后一条通知时间，并存储在 session 中
			int sidx = (Integer)session.getAttribute("sidx");
			Date lastTime = null;
			try {
				lastTime = memberService.lastAlarmTime(sidx);

				if (lastTime != null) {
					Timestamp timestampDate = new Timestamp(lastTime.getTime());
					session.setAttribute("lastTime", timestampDate);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			mav.setViewName("redirect:/catstagram"); // 登录成功后跳转到主页面
		} else if (result == memberService.LOGIN_FAIL || result == memberService.ERROR) {
			mav.addObject("msg", "请确认您的用户名或密码。");
			mav.addObject("goUrl", "/catstagram");
			mav.setViewName("msg/msg");
		} else if (result == memberService.QUIT) {
			mav.addObject("msg", "该账户已无法登录。");
			mav.addObject("goUrl", "/catstagram");
			mav.setViewName("msg/msg");
		}
		return mav;
	}

	@ApiOperation(value = "拒绝 GET 请求的登录行为", notes = "非法访问提示")
	@GetMapping("/catstagram/account/login")
	public ModelAndView loginGetReq(HttpServletRequest req) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("msg", "非法访问。");
		mav.addObject("goUrl", "/catstagram");
		mav.setViewName("msg/msg");
		return mav;
	}
}
