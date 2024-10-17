package com.catstagram.service;

import java.util.Date;
import java.util.List;

import com.catstagram.entity.Alarm;
import com.catstagram.entity.Member;

public interface MemberService {
	
	int LOGIN_OK = 1; // 登录成功
	int LOGIN_FAIL = 2; // 用户名或密码错误时
	int QUIT = 3; // 会员退出的情况
	int ERROR = 4;
	
	public String idCheck(String id) throws Exception;
	public int signup(Member dto) throws Exception;
	public int login(String id, String pwd) throws Exception;
	public Member sessionInfo(String id) throws Exception;
	public List<Member> searchList(String search_id, String sid, int sidx) throws Exception;
	public String profileInfo(int member_idx) throws Exception;
	public int profileUpdate(Member dto) throws Exception;
	public int profileImgUpdate(Member dto) throws Exception;
	public int infoUpdate(Member dto) throws Exception;
	public String pwdChk(int sidx) throws Exception;
	public int pwdUpdateOk(Member dto) throws Exception;
	public int quit(int sidx) throws Exception;
	public List<Member> followerList(int sidx) throws Exception;
	public List<Member> followingList(int sidx) throws Exception;
	public Member urlIdxIdSelect(String member_id, int sidx) throws Exception;
	public Member followListOtherInfo(String member_id) throws Exception;
	public List<Member> otherFollowerList(int member_idx, int sidx) throws Exception;
	public List<Member> otherFollowingList(int member_idx, int sidx) throws Exception;
	public List<Alarm> alarmList(int sidx) throws Exception;
	public Date lastAlarmTime(int sidx) throws Exception;
	public int lastAlarmSave(java.sql.Timestamp lastTime, int sidx) throws Exception;
}