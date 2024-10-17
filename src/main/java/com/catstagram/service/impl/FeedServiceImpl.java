package com.catstagram.service.impl;

import java.util.*;

import com.catstagram.service.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.catstagram.entity.CommentLike;
import com.catstagram.entity.MainFollowingFeed;
import com.catstagram.entity.Feed;
import com.catstagram.entity.FeedLike;
import com.catstagram.mapper.CommentLikeMapper;
import com.catstagram.mapper.CommentMapper;
import com.catstagram.mapper.FeedLikeMapper;
import com.catstagram.mapper.FeedMapper;

@Service
public class FeedServiceImpl implements FeedService {

    @Autowired
    private FeedMapper mapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private FeedLikeMapper feedLikeMapper;

    @Autowired
    private CommentLikeMapper commentLikeMapper;

    // 在主页面上显示关注的朋友发布的动态和我发布的动态
    @Override
    public List<MainFollowingFeed> mainFollowingFeed(int sidx) throws Exception {
        List<MainFollowingFeed> dto = mapper.mainFollowingFeed(sidx);
        // 我是否点赞了该动态
        for (int i = 0; i < dto.size(); i++) {
            FeedLike fldto = new FeedLike();
            fldto.setFeed_idx(dto.get(i).getFeed_idx());
            fldto.setMember_idx(sidx);
            dto.get(i).setFeed_like_idx(feedLikeMapper.feedLikeYN(fldto));
        }

        // 该动态的评论列表
        for (int i = 0; i < dto.size(); i++) {
            dto.get(i).setFeed_comment_list(commentMapper.feedCommentList(dto.get(i).getFeed_idx()));
            for (int j = 0; j < dto.get(i).getFeed_comment_list().size(); j++) {
                if (dto.get(i).getFeed_comment_list().get(j) != null) {

                    // 评论发布不到1小时
                    if (dto.get(i).getFeed_comment_list().get(j).getComment_date_minute() < 60) {
                        dto.get(i).getFeed_comment_list().get(j).setComment_date_time(dto.get(i).getFeed_comment_list().get(j).getComment_date_minute() + "分钟");
                        // 评论发布不到24小时
                    } else if (dto.get(i).getFeed_comment_list().get(j).getComment_date_minute() >= 60 && dto.get(i).getFeed_comment_list().get(j).getComment_date_minute() < 1440) {
                        dto.get(i).getFeed_comment_list().get(j).setComment_date_time((int) Math.floor(dto.get(i).getFeed_comment_list().get(j).getComment_date_minute() / 60) + "小时");
                        // 评论发布超过24小时
                    } else if (dto.get(i).getFeed_comment_list().get(j).getComment_date_minute() >= 1440 && dto.get(i).getFeed_comment_list().get(j).getComment_date_minute() < 10080) {
                        dto.get(i).getFeed_comment_list().get(j).setComment_date_time((int) Math.floor(dto.get(i).getFeed_comment_list().get(j).getComment_date_minute() / 1440) + "天");
                        // 评论发布超过7天
                    } else if (dto.get(i).getFeed_comment_list().get(j).getComment_date_minute() >= 10080) {
                        dto.get(i).getFeed_comment_list().get(j).setComment_date_time((int) Math.floor(dto.get(i).getFeed_comment_list().get(j).getComment_date_minute() / 10080) + "周");
                    }

                    // 我是否点赞了该评论
                    CommentLike cldto = new CommentLike();
                    cldto.setComment_idx(dto.get(i).getFeed_comment_list().get(j).getComment_idx());
                    cldto.setMember_idx(sidx);
                    dto.get(i).getFeed_comment_list().get(j).setComment_like_idx(commentLikeMapper.feedLikeCommentYN(cldto));
                }
            }
        }
        return dto;
    }

    // 发布动态
    @Override
    public int feedWrite(Feed dto) throws Exception {
        int result = mapper.feedWrite(dto);
        return result;
    }

    // 获取要修改的动态信息
    @Override
    public Feed feedUpdateInfo(int feed_idx) throws Exception {
        Feed dto = mapper.feedUpdateInfo(feed_idx);
        return dto;
    }

    // 获取动态发布者的idx（在跳转到动态修改页面时需要）
    @Override
    public Integer feedMemberIdx(int feed_idx) throws Exception {
        Integer result = mapper.feedMemberIdx(feed_idx) != null ? mapper.feedMemberIdx(feed_idx) : 0;
        return result;
    }

    // 修改动态
    @Override
    public int feedUpdate(Feed dto) throws Exception {
        int result = mapper.feedUpdate(dto);
        return result;
    }

    // 删除动态
    @Override
    public int feedDel(int feed_idx) throws Exception {
        int result = mapper.feedDel(feed_idx);
        return result;
    }

    // 增加动态点赞数
    @Override
    public int feedLikeCountPlus(int feed_idx) throws Exception {
        int result = mapper.feedLikeCountPlus(feed_idx);
        return result;
    }

    // 减少动态点赞数
    @Override
    public int feedLikeCountMinus(int feed_idx) throws Exception {
        int result = mapper.feedLikeCountMinus(feed_idx);
        return result;
    }

    // 获取动态点赞数
    @Override
    public int feedLikeCount(int feed_idx) throws Exception {
        int result = mapper.feedLikeCount(feed_idx);
        return result;
    }

    // 获取catstagram动态列表
    @Override
    public List<MainFollowingFeed> feedList(int member_idx, int sidx) throws Exception {
        List<MainFollowingFeed> dto = mapper.feedList(member_idx);

        // 我是否点赞了该动态
        for (int i = 0; i < dto.size(); i++) {
            if (dto.get(i) != null) {
                FeedLike fldto = new FeedLike();
                fldto.setFeed_idx(dto.get(i).getFeed_idx());
                fldto.setMember_idx(sidx);
                dto.get(i).setFeed_like_idx(feedLikeMapper.feedLikeYN(fldto));
            }
        }

        // 该动态的评论列表
        for (int i = 0; i < dto.size(); i++) {
            if (dto.get(i) != null) {
                dto.get(i).setFeed_comment_list(commentMapper.feedCommentList(dto.get(i).getFeed_idx()));
                for (int j = 0; j < dto.get(i).getFeed_comment_list().size(); j++) {
                    if (dto.get(i).getFeed_comment_list().get(j) != null) {
                        // 评论发布不到1小时
                        if (dto.get(i).getFeed_comment_list().get(j).getComment_date_minute() < 60) {
                            dto.get(i).getFeed_comment_list().get(j).setComment_date_time(dto.get(i).getFeed_comment_list().get(j).getComment_date_minute() + "分钟");
                            // 评论发布不到24小时
                        } else if (dto.get(i).getFeed_comment_list().get(j).getComment_date_minute() >= 60 && dto.get(i).getFeed_comment_list().get(j).getComment_date_minute() < 1440) {
                            dto.get(i).getFeed_comment_list().get(j).setComment_date_time((int) Math.floor(dto.get(i).getFeed_comment_list().get(j).getComment_date_minute() / 60) + "小时");
                            // 评论发布超过24小时
                        } else if (dto.get(i).getFeed_comment_list().get(j).getComment_date_minute() >= 1440 && dto.get(i).getFeed_comment_list().get(j).getComment_date_minute() < 10080) {
                            dto.get(i).getFeed_comment_list().get(j).setComment_date_time((int) Math.floor(dto.get(i).getFeed_comment_list().get(j).getComment_date_minute() / 1440) + "天");
                            // 评论发布超过7天
                        } else if (dto.get(i).getFeed_comment_list().get(j).getComment_date_minute() >= 10080) {
                            dto.get(i).getFeed_comment_list().get(j).setComment_date_time((int) Math.floor(dto.get(i).getFeed_comment_list().get(j).getComment_date_minute() / 10080) + "周");
                        }

                        // 我是否点赞了该评论
                        CommentLike cldto = new CommentLike();
                        cldto.setComment_idx(dto.get(i).getFeed_comment_list().get(j).getComment_idx());
                        cldto.setMember_idx(sidx);
                        dto.get(i).getFeed_comment_list().get(j).setComment_like_idx(commentLikeMapper.feedLikeCommentYN(cldto));
                    }
                }
            }
        }
        return dto;
    }
}