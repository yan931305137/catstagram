package com.catstagram.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.catstagram.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
	// 获取指定feed的评论列表
	List<Comment> feedCommentList(int feed_idx);

	// 插入一条新的评论
	int feedCommentInsert(Comment dto);

	// 删除指定评论
	int feedCommentDel(int comment_idx);

	// 增加指定评论的点赞数
	int feedCommentLikeCountPlus(int comment_idx);

	// 减少指定评论的点赞数
	int feedCommentLikeCountMinus(int feed_idx);

	// 获取指定feed的评论总点赞数
	int feedCommentLikeCount(int feed_idx);

}