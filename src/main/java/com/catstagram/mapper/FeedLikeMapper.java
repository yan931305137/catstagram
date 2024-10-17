package com.catstagram.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.catstagram.entity.FeedLike;

public interface FeedLikeMapper extends BaseMapper<FeedLike> {
	// 点赞Feed
	int likeFeed(FeedLike dto);

	// 取消点赞Feed
	int likeFeedCancel(FeedLike dto);

	// 查询Feed是否被点赞
	Integer feedLikeYN(FeedLike dto);

}