package com.catstagram.service.impl;

import java.util.List;

import com.catstagram.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.catstagram.entity.Comment;
import com.catstagram.mapper.CommentMapper;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	public CommentMapper mapper;
	
	// 帖子评论列表
	@Override
	public List<Comment> feedCommentList(int feed_idx) throws Exception {
		List<Comment> dto = mapper.feedCommentList(feed_idx);
		return dto;
	}
	
	// 帖子评论撰写
	@Override
	public int feedCommentInsert(Comment dto) throws Exception {
		int result = mapper.feedCommentInsert(dto);
		return result;
	}
	
	// 帖子评论删除
	@Override
	public int feedCommentDel(int comment_idx) throws Exception {
		int result = mapper.feedCommentDel(comment_idx);
		return result;
	}
	
	// 帖子评论点赞数增加
	@Override
	public int feedCommentLikeCountPlus(int comment_idx) throws Exception {
		int result = mapper.feedCommentLikeCountPlus(comment_idx);
		return result;
	}
	
	// 帖子评论点赞数减少
	@Override
	public int feedCommentLikeCountMinus(int comment_idx) throws Exception {
		int result = mapper.feedCommentLikeCountMinus(comment_idx);
		return result;
	}
	
	// 帖子评论点赞数
	@Override
	public int feedCommentLikeCount(int comment_idx) throws Exception {
		int result = mapper.feedCommentLikeCount(comment_idx);
		return result;
	}
}