package com.catstagram.entity;

import java.io.Serializable;
import java.sql.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName("catstagram_member")
public class Member implements Serializable {
	// 用户的唯一标识符（索引）
	@TableId
	private Integer member_idx;
	// 用户ID
	private String member_id;
	// 用户名
	private String member_name;
	// 用户密码
	private String member_pwd;
	// 用户邮箱
	private String member_email;
	// 用户简介
	private String member_intro;
	// 用户头像链接
	private String member_img;
	// 用户加入日期和时间
	private Date member_joindate;
	// 用户退出状态
	private String member_quit;
	// 是否关注该用户（0表示未关注，1表示已关注）
	private Integer is_follow;
	// 用户的动态数量
	private Integer feed_count;
	// 用户关注的人数
	private Integer following_count;
	// 用户的粉丝数
	private Integer follower_count;
	// 用户的动态数量（以千为单位）
	private String feed_count_KM;
	// 用户关注的人数（以千为单位）
	private String following_count_KM;
	// 用户的粉丝数（以千为单位）
	private String follower_count_KM;
}