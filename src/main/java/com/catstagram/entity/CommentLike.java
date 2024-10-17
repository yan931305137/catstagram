package com.catstagram.entity;

import java.io.Serializable;
import java.sql.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName("catstagram_comment_like")
public class CommentLike implements Serializable {
	// 评论收到的点赞的唯一标识符（索引）
	@TableId
	private int comment_like_idx;
	// 评论的唯一标识符（索引）
	private int comment_idx;
	// 发表评论的用户的唯一标识符（索引）
	private int member_idx;
	// 评论收到点赞的日期和时间
	private Date comment_like_date;

}