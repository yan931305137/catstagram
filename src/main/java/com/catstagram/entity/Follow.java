package com.catstagram.entity;

import java.io.Serializable;
import java.sql.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName("catstagram_follow")
public class Follow implements Serializable {
	// 关注的唯一标识符（索引）
	@TableId
	private int follow_idx;
	// 发起关注的用户的唯一标识符（索引）
	private int member_from;
	// 被关注的用户的唯一标识符（索引）
	private int member_to;
	// 关注日期和时间
	private Date follow_date;
	// 我自己关注的人中，这个人关注的人的唯一标识符（索引）
	private int following_idx_of_my_following;
	// 我自己关注的人中，这个人关注的人的用户ID
	private String following_id_of_my_following;
	// 我自己关注的人中，这个人关注的人的用户名称
	private String following_name_of_my_following;
	// 我自己关注的人中，这个人关注的人的用户头像链接
	private String following_img_of_my_following;
	// 我自己关注的人中，有多少人关注了这个人（例如：“关注 1人”表示只有我关注了他，所以减1）
	private int num_of_followers;
	// 我自己关注的人中，这些人关注了这个人的列表（字符串形式）
	private String my_following_list;
	// 我自己关注的人中，这些人关注了这个人的列表（数组形式）
	private String[] my_following_list_arr;

}