package com.catstagram.service;

import java.util.*;

import com.catstagram.entity.Follow;

public interface FollowService {
	public int following(Follow dto) throws Exception;
	public int cancelFollowing(Follow dto) throws Exception;
	public int delFollower(int member_idx, int sidx) throws Exception;
	public List<Follow> suggestedFollows(int sidx) throws Exception;
	public List<Follow> suggestedFollowersInMain(int sidx) throws Exception;
	public int otherFollowerCount(int member_idx) throws Exception;
	public Follow whoFollow(int member_idx, int sidx) throws Exception;
}