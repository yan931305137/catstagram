package com.catstagram.service.impl;

import com.catstagram.service.FeedLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.catstagram.entity.FeedLike;
import com.catstagram.mapper.FeedLikeMapper;

@Service
public class FeedLikeServiceImpl implements FeedLikeService {
	
	@Autowired
	private FeedLikeMapper mapper;
	
	// 点赞
	@Override
	public int likeFeed(FeedLike dto) throws Exception {
		int result = mapper.likeFeed(dto);
		return result;
	}
	
	// 取消点赞
	@Override
	public int likeFeedCancel(FeedLike dto) throws Exception {
		int result = mapper.likeFeedCancel(dto);
		return result;
	}
	
	// 我是否点赞了这条动态
	@Override
	public Integer feedLikeYN(FeedLike dto) throws Exception {
		Integer result = mapper.feedLikeYN(dto);
		return result;
	}
}