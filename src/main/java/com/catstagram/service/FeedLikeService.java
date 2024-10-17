package com.catstagram.service;

import com.catstagram.entity.FeedLike;

public interface FeedLikeService {
	public int likeFeed(FeedLike dto) throws Exception;
	public int likeFeedCancel(FeedLike dto) throws Exception;
	public Integer feedLikeYN(FeedLike dto) throws Exception;
}