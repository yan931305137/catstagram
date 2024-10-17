package com.catstagram.entity;

import java.io.Serializable;
import java.sql.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName("catstagram_feed")
public class Feed implements Serializable {
	// 动态的唯一标识符（索引）
	@TableId
	private Integer feed_idx;
	// 发布动态的用户的唯一标识符（索引）
	private int member_idx;
	// 动态图片链接
	private String feed_img;
	// 动态内容
	private String feed_content;
	// 动态收到的点赞数量
	private int feed_like_count;
	// 动态发布日期和时间
	private Date feed_date;

}