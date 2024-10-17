package com.catstagram.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.catstagram.entity.CommentLike;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentLikeMapper extends BaseMapper<CommentLike> {
	// 点赞评论
	int likeFeedComment(CommentLike dto);

	// 取消点赞评论
	int likeFeedCommentCancel(CommentLike dto);

	// 查询评论是否被点赞
	Integer feedLikeCommentYN(CommentLike dto);

}