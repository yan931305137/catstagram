package com.catstagram.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.catstagram.entity.Comment;
import com.catstagram.service.CommentService;
import com.catstagram.entity.CommentLike;
import com.catstagram.service.CommentLikeService;

// 评论管理控制器
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentLikeService commentLikeService;

    // 发表帖子评论
    @ResponseBody
    @PostMapping("/catstagram/account/feedCommentInsert")
    public List<Comment> feedCommentInsert(@RequestParam("feed_idx") int feed_idx,
                                           @RequestParam("comment_content") String comment_content,
                                           HttpSession session) {
        int sidx = (Integer) session.getAttribute("sidx");
        Comment dto = new Comment();
        dto.setMember_idx(sidx);
        dto.setFeed_idx(feed_idx);
        dto.setComment_content(comment_content);

        try {
            commentService.feedCommentInsert(dto);

        } catch (Exception e) {
            e.printStackTrace();
        }

        List<Comment> list = null;
        try {
            list = commentService.feedCommentList(feed_idx);

            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) != null) {

                    // 评论发表时间少于1小时
                    if (list.get(i).getComment_date_minute() < 60) {
                        list.get(i).setComment_date_time(list.get(i).getComment_date_minute() + "分钟");
                        // 评论发表时间少于24小时
                    } else if (list.get(i).getComment_date_minute() >= 60 && list.get(i).getComment_date_minute() < 1440) {
                        list.get(i).setComment_date_time((int) Math.floor(list.get(i).getComment_date_minute() / 60) + "小时");
                        // 评论发表时间超过24小时
                    } else if (list.get(i).getComment_date_minute() >= 1440 && list.get(i).getComment_date_minute() < 10080) {
                        list.get(i).setComment_date_time((int) Math.floor(list.get(i).getComment_date_minute() / 1440) + "天");
                        // 评论发表时间超过7天
                    } else if (list.get(i).getComment_date_minute() >= 10080) {
                        list.get(i).setComment_date_time((int) Math.floor(list.get(i).getComment_date_minute() / 10080) + "周");
                    }

                    // 是否点赞了这条评论
                    CommentLike cldto = new CommentLike();
                    cldto.setComment_idx(list.get(i).getComment_idx());
                    cldto.setMember_idx(sidx);
                    list.get(i).setComment_like_idx(commentLikeService.feedLikeCommentYN(cldto));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 删除帖子评论
    @PostMapping("/catstagram/account/feedCommentDel")
    public String feedCommentDel(@RequestParam("comment_idx") int comment_idx) {
        try {
            commentService.feedCommentDel(comment_idx);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "main";
    }

    // 点赞帖子评论
    @ResponseBody
    @PostMapping("/catstagram/account/likeFeedComment")
    public int likeFeedComment(@RequestParam("comment_idx") int comment_idx, HttpSession session) {
        int sidx = (Integer) session.getAttribute("sidx");
        CommentLike dto = new CommentLike();
        dto.setComment_idx(comment_idx);
        dto.setMember_idx(sidx);

        int commentLikeCount = 0;
        try {
            commentLikeService.likeFeedComment(dto);
            commentService.feedCommentLikeCountPlus(comment_idx);
            commentLikeCount = commentService.feedCommentLikeCount(comment_idx);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return commentLikeCount;
    }

    // 取消点赞帖子评论
    @ResponseBody
    @PostMapping("/catstagram/account/likeFeedCommentCancel")
    public int likeFeedCommentCancel(@RequestParam("comment_idx") int comment_idx, HttpSession session) {
        int sidx = (Integer) session.getAttribute("sidx");
        CommentLike dto = new CommentLike();
        dto.setComment_idx(comment_idx);
        dto.setMember_idx(sidx);

        int commentLikeCount = 0;
        try {
            commentLikeService.likeFeedCommentCancel(dto);
            commentService.feedCommentLikeCountMinus(comment_idx);
            commentLikeCount = commentService.feedCommentLikeCount(comment_idx);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return commentLikeCount;
    }
}