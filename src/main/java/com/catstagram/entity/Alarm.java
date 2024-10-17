package com.catstagram.entity;

import java.io.Serializable;
import java.sql.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName("catstagram_alarm")
public class Alarm implements Serializable {
	// 活动类型
	private String activity_type;
	// 用户ID
	private String member_id;
	// 关注的唯一标识符（索引）
	private int follow_idx;
	// 动态的唯一标识符（索引）
	private int feed_idx;
	// 动态图片链接
	private String feed_img;
	// 点赞的唯一标识符（索引）
	private int feed_like_idx;
	// 评论的唯一标识符（索引）
	private int comment_idx;
	// 评论内容
	private String comment_content;
	// 评论收到的点赞的唯一标识符（索引）
	private int comment_like_idx;
	// 提醒日期和时间
	private Date alarm_date;
	// 提醒时间的分钟数
	private int alarm_date_minute;
	// 提醒时间的字符串表示（例如：5分前、3小时前等）
	private String alarm_date_string;

}