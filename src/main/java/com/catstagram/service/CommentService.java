package com.catstagram.service;

import java.util.List;

import com.catstagram.entity.Comment;

public interface CommentService {
	public List<Comment> feedCommentList(int feed_idx) throws Exception;
	public int feedCommentInsert(Comment dto) throws Exception;
	public int feedCommentDel(int comment_idx) throws Exception;
	public int feedCommentLikeCountPlus(int comment_idx) throws Exception;
	public int feedCommentLikeCountMinus(int comment_idx) throws Exception;
	public int feedCommentLikeCount(int comment_idx) throws Exception;
}