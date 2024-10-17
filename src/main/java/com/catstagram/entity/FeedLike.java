package com.catstagram.entity;

import java.io.Serializable;
import java.sql.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName("catstagram_feed_like")
public class FeedLike implements Serializable {
	// 点赞的唯一标识符（索引）
	@TableId
	private int feed_like_idx;
	// 动态的唯一标识符（索引）
	private int feed_idx;
	// 用户的唯一标识符（索引）
	private int member_idx;
	// 点赞日期和时间
	private Date feed_like_date;

}