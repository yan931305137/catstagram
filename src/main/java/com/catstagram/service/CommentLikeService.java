package com.catstagram.service;

import com.catstagram.entity.CommentLike;

public interface CommentLikeService {
	public int likeFeedComment(CommentLike dto) throws Exception;
	public int likeFeedCommentCancel(CommentLike dto) throws Exception;
	public Integer feedLikeCommentYN(CommentLike dto) throws Exception;
}