package com.catstagram.service.impl;

import java.util.*;

import com.catstagram.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.catstagram.entity.Alarm;
import com.catstagram.mapper.MemberMapper;
import com.catstagram.entity.Member;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	MemberMapper mapper;

	// 检查ID是否重复
	@Override
	public String idCheck(String id) throws Exception {
		String result = mapper.idCheck(id);
		return result;
	}
	
	// 注册
	@Override
	public int signup(Member dto) throws Exception {
		int result = mapper.signup(dto);
		return result;
	}
	
	// 登录
	@Override
	public int login(String id, String pwd) throws Exception {
		Member dto = mapper.login(id);
		int result = 0;
		if(dto != null) {
			if(dto.getMember_pwd().equals(pwd)) {
				if(dto.getMember_quit().equals("n")) {
					result = LOGIN_OK; // 登录成功
				} else {
					result = QUIT; // 用户已注销
				}
			} else {
				result = LOGIN_FAIL; // 密码错误
			}
		} else {
			return ERROR; // 数据库中不存在该ID
		}
		return result;
	}
	
	// 会话信息
	@Override
	public Member sessionInfo(String id) throws Exception {
		Member dto = mapper.sessionInfo(id);
		return dto;
	}
	
	// 搜索会员时查询列表
	@Override
	public List<Member> searchList(String search_id, String sid, int sidx) throws Exception {
		Map map = new HashMap();
		map.put("search_id", search_id);
		map.put("sid", sid);
		map.put("sidx", sidx);
		List<Member> dto = mapper.searchList(map);
		return dto;
	}
	
	// 加载已保存的个人资料
	@Override
	public String profileInfo(int member_idx) throws Exception {
		String result = mapper.profileInfo(member_idx);
		return result;
	}
	
	// 修改个人资料
	@Override
	public int profileUpdate(Member dto) throws Exception {
		int result = mapper.profileUpdate(dto);
		return result;
	}
	
	// 更改个人资料图片
	@Override
	public int profileImgUpdate(Member dto) throws Exception {
		int result = mapper.profileImgUpdate(dto);
		return result;
	}
	
	// 修改会员信息
	@Override
	public int infoUpdate(Member dto) throws Exception {
		int result = mapper.infoUpdate(dto);
		return result;
	}
	
	// 确认密码（在修改会员信息时）
	@Override
	public String pwdChk(int sidx) throws Exception {
		String result = mapper.pwdChk(sidx);
		return result;
	}
	
	// 更改密码
	@Override
	public int pwdUpdateOk(Member dto) throws Exception {
		int result = mapper.pwdUpdateOk(dto);
		return result;
	}
	
	// 注销会员
	@Override
	public int quit(int sidx) throws Exception {
		int result = mapper.quit(sidx);
		return result;
	}
	
	// 关注者列表（加我为好友的人）
	@Override
	public List<Member> followerList(int sidx) throws Exception {
		List<Member> dto = mapper.followerList(sidx);
		return dto;
	}
	
	// 关注列表（我加为好友的人）
	@Override
	public List<Member> followingList(int sidx) throws Exception {
		List<Member> dto = mapper.followingList(sidx);
		return dto;
	}
	
	// 查询会员的Catstagram地址的idx和ID及其他信息
	@Override
	public Member urlIdxIdSelect(String member_id, int sidx) throws Exception {
		Map map = new HashMap();
		map.put("member_id", member_id);
		map.put("sidx", sidx);
		Member dto = mapper.urlIdxIdSelect(map);
		return dto;
	}
	
	// 查询会员的关注者和关注列表的idx和ID
	@Override
	public Member followListOtherInfo(String member_id) throws Exception {
		Member dto = mapper.followListOtherInfo(member_id);
		return dto;
	}
	
	// 其他会员的关注者列表
	@Override
	public List<Member> otherFollowerList(int member_idx, int sidx) throws Exception {
		Map map = new HashMap();
		map.put("member_idx", member_idx);
		map.put("sidx", sidx);
		List<Member> list = mapper.otherFollowerList(map);
		return list;
	}
	
	// 其他会员的关注列表
	@Override
	public List<Member> otherFollowingList(int member_idx, int sidx) throws Exception {
		Map map = new HashMap();
		map.put("member_idx", member_idx);
		map.put("sidx", sidx);
		List<Member> list = mapper.otherFollowingList(map);
		return list;
	}
	
	// Header的通知列表
	@Override
	public List<Alarm> alarmList(int sidx) throws Exception {
		List<Alarm> list = mapper.alarmList(sidx);
		return list;
	}
	
	// 我看到的通知中最后一个通知的时间
	@Override
	public Date lastAlarmTime(int sidx) throws Exception {
		Date lastTime = mapper.lastAlarmTime(sidx);
		return lastTime;
	}
	
	// 将最后一个通知的时间保存到数据库
	@Override
	public int lastAlarmSave(java.sql.Timestamp lastTime, int sidx) throws Exception {
		Map map = new HashMap();
		map.put("lastTime", lastTime);
		map.put("sidx", sidx);
		int result = mapper.lastAlarmSave(map);
		return result;
	}
}