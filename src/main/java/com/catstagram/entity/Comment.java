package com.catstagram.entity;

import java.io.Serializable;
import java.sql.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("catstagram_comment")
public class Comment implements Serializable {
	// 评论的唯一标识符（索引）
	@TableId
	private int comment_idx;
	// 与评论相关联的动态的唯一标识符（索引）
	private int feed_idx;
	// 发表评论的用户的唯一标识符（索引）
	private int member_idx;
	// 评论的内容
	private String comment_content;
	// 评论收到的点赞数量
	private int comment_like_count;
	// 评论的日期和时间
	private Date comment_date;
	// 发表评论的用户ID
	private String member_id;
	// 发表评论的用户头像图片链接
	private String member_img;
	// 评论的分钟数（可能是用于排序或筛选）
	private int comment_date_minute;
	// 评论的具体时间（可能是字符串格式）
	private String comment_date_time;
	// 评论收到的点赞的唯一标识符（索引），可能为空
	private Integer comment_like_idx;
}