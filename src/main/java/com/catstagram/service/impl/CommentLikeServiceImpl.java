package com.catstagram.service.impl;

import com.catstagram.service.CommentLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.catstagram.entity.CommentLike;
import com.catstagram.mapper.CommentLikeMapper;

@Service
public class CommentLikeServiceImpl implements CommentLikeService {
	
	@Autowired
	private CommentLikeMapper mapper;
	
	// 点赞评论
	@Override
	public int likeFeedComment(CommentLike dto) throws Exception {
		int result = mapper.likeFeedComment(dto);
		return result;
	}
	
	// 取消点赞评论
	@Override
	public int likeFeedCommentCancel(CommentLike dto) throws Exception {
		int result = mapper.likeFeedCommentCancel(dto);
		return result;
	}
	
	// 我是否点赞了这条评论
	@Override
	public Integer feedLikeCommentYN(CommentLike dto) throws Exception {
		Integer result = mapper.feedLikeCommentYN(dto);
		return result;
	}
}