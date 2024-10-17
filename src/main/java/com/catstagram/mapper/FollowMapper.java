package com.catstagram.mapper;

import java.util.*;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.catstagram.entity.Follow;

public interface FollowMapper extends BaseMapper<Follow> {
	// 关注某个用户
	int following(Follow dto);

	// 取消关注某个用户
	int cancelFollowing(Follow dto);

	// 删除某个用户的跟随者
	int delFollower(Map map);

	// 获取推荐的跟随者列表
	List<Follow> suggestedFollows(int sidx);

	// 在主页上获取推荐的跟随者列表
	List<Follow> suggestedFollowersInMain(int sidx);

	// 获取某个用户的关注者数量
	int otherFollowerCount(int member_idx);

	// 查询某个用户关注了哪些用户
	Follow whoFollow(Map map);

}