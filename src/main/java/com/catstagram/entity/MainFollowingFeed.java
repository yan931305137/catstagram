package com.catstagram.entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName("catstagram_mainfollowingfeed")
public class MainFollowingFeed implements Serializable {
	// 用户的唯一标识符（索引）
	private int member_idx;
	// 用户ID
	private String member_id;
	// 用户头像图片链接
	private String member_img;
	// 动态的唯一标识符（索引）
	@TableId
	private int feed_idx;
	// 动态图片链接
	private String feed_img;
	// 动态内容
	private String feed_content;
	// 动态收到的点赞数量
	private int feed_like_count;
	// 动态发布日期和时间
	private Date feed_date;
	// 动态的分钟数（可能是用于排序或筛选）
	private int feed_date_minute;
	// 动态的具体时间（可能是字符串格式）
	private String feed_date_time;
	// 该动态的评论列表
	private List<Comment> feed_comment_list;
	// 我是否对该动态点赞的标识符（索引），可能为空
	private Integer feed_like_idx;
}