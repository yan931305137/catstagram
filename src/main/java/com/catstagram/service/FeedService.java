package com.catstagram.service;

import java.util.*;

import com.catstagram.entity.MainFollowingFeed;
import com.catstagram.entity.Feed;

public interface FeedService {
	public List<MainFollowingFeed> mainFollowingFeed(int sidx) throws Exception;
	public int feedWrite(Feed dto) throws Exception;
	public Integer feedMemberIdx(int feed_idx) throws Exception;
	public Feed feedUpdateInfo(int feed_idx) throws Exception;
	public int feedUpdate(Feed dto) throws Exception;
	public int feedDel(int feed_idx) throws Exception;
	public int feedLikeCountPlus(int feed_idx) throws Exception;
	public int feedLikeCountMinus(int feed_idx) throws Exception;
	public int feedLikeCount(int feed_idx) throws Exception;
	public List<MainFollowingFeed> feedList(int member_idx, int sidx) throws Exception;
}