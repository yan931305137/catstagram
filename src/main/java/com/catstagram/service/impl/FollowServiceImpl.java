package com.catstagram.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.catstagram.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.catstagram.entity.Follow;
import com.catstagram.mapper.FollowMapper;

@Service
public class FollowServiceImpl implements FollowService {
	
	@Autowired
	private FollowMapper mapper;
	
	// 关注（添加好友）
	@Override
	public int following(Follow dto) throws Exception {
		int result = mapper.following(dto);
		return result;
	}
	
	// 取消关注（删除好友）
	@Override
	public int cancelFollowing(Follow dto) throws Exception {
		int result = mapper.cancelFollowing(dto);
		return result;
	}
	
	// 从关注者列表中删除关注我的人
	@Override
	public int delFollower(int member_idx, int sidx) throws Exception {
		Map map = new HashMap();
		map.put("member_from", member_idx);
		map.put("member_to", sidx);
		int result = mapper.delFollower(map);
		return result;
	}
	
	// 推荐关注（推荐好友，最多100人）
	@Override
	public List<Follow> suggestedFollows(int sidx) throws Exception {
		List<Follow> list = mapper.suggestedFollows(sidx);
		return list;
	}
	
	// 随机推荐3人关注（在主页随机推荐3个好友）
	@Override
	public List<Follow> suggestedFollowersInMain(int sidx) throws Exception {
		List<Follow> list = mapper.suggestedFollowersInMain(sidx);
		return list;
	}
	
	// 其他会员的关注者数量（为了知道我关注该会员后的增减变化）
	@Override
	public int otherFollowerCount(int member_idx) throws Exception {
		int result = mapper.otherFollowerCount(member_idx);
		return result;
	}
	
	// 这个会员在我的关注者中谁关注了他
	@Override
	public Follow whoFollow(int member_idx, int sidx) throws Exception {
		Map map = new HashMap();
		map.put("member_idx", member_idx);
		map.put("sidx", sidx);
		Follow dto = mapper.whoFollow(map);
		return dto;
	}
}