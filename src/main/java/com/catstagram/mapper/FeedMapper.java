package com.catstagram.mapper;

import java.util.*;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.catstagram.entity.MainFollowingFeed;
import com.catstagram.entity.Feed;

public interface FeedMapper extends BaseMapper<Feed> {
	// 获取指定用户的主Feed列表
	List<MainFollowingFeed> mainFollowingFeed(int sidx);

	// 发布一条新的Feed
	int feedWrite(Feed dto);

	// 获取指定Feed的作者ID
	Integer feedMemberIdx(int feed_idx);

	// 更新指定Feed的信息
	Feed feedUpdateInfo(int feed_idx);

	// 更新指定Feed的内容
	int feedUpdate(Feed dto);

	// 删除指定Feed
	int feedDel(int feed_idx);

	// 增加指定Feed的点赞数
	int feedLikeCountPlus(int feed_idx);

	// 减少指定Feed的点赞数
	int feedLikeCountMinus(int feed_idx);

	// 获取指定Feed的点赞数
	int feedLikeCount(int feed_idx);

	// 获取指定用户的所有Feed列表
	List<MainFollowingFeed> feedList(int member_idx);

}