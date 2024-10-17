package com.catstagram.mapper;

import java.util.*;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.catstagram.entity.Alarm;
import com.catstagram.entity.Member;


public interface MemberMapper extends BaseMapper<Member> {
	// 检查ID是否可用
	String idCheck(String id);

	// 注册新用户
	int signup(Member dto);

	// 登录验证
	Member login(String id);

	// 获取会话信息
	Member sessionInfo(String id);

	// 搜索成员列表
	List<Member> searchList(Map map);

	// 获取个人资料信息
	String profileInfo(int member_idx);

	// 更新个人资料
	int profileUpdate(Member dto);

	// 更新个人头像
	int profileImgUpdate(Member dto);

	// 更新个人信息
	int infoUpdate(Member dto);

	// 检查密码是否正确
	String pwdChk(int sidx);

	// 更新密码成功
	int pwdUpdateOk(Member dto);

	// 退出账号
	int quit(int sidx);

	// 获取关注者列表
	List<Member> followerList(int sidx);

	// 获取关注列表
	List<Member> followingList(int sidx);

	// 根据URL索引和ID选择成员
	Member urlIdxIdSelect(Map map);

	// 获取其他成员的关注列表和关注者列表信息
	Member followListOtherInfo(String member_id);

	// 获取其他成员的关注者列表
	List<Member> otherFollowerList(Map map);

	// 获取其他成员的关注列表
	List<Member> otherFollowingList(Map map);

	// 获取提醒列表
	List<Alarm> alarmList(int sidx);

	// 获取最后一条提醒的时间
	Date lastAlarmTime(int sidx);

	// 保存最后一条提醒的时间
	int lastAlarmSave(Map map);

}