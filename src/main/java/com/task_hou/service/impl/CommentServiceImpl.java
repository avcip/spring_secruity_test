package com.task_hou.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.task_hou.entity.Comment;
import com.task_hou.mapper.CommentMapper;
import com.task_hou.service.CommentService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Override
    public Comment postComment(Comment comment) {
        // 自动填充默认值：评论时间、点赞数、状态
        comment.setCommentTime(LocalDateTime.now());
        comment.setLikeCount(0);
        comment.setStatus("active");
        // 调用BaseMapper的insert方法保存（继承自ServiceImpl）
        save(comment);
        System.out.println("66666");
        return comment;
    }

    @Override
    public IPage<Comment> getCommentList(Integer forumId, Integer knowledgeId, Integer caseId, Integer page, Integer pageSize) {
        // 初始化分页参数（处理page为null的情况）
        Page<Comment> pageParam = new Page<>(page == null ? 1 : page, pageSize == null ? 10 : pageSize);
        // 调用自定义Mapper方法查询
        return baseMapper.selectByRelatedId(pageParam, forumId, knowledgeId, caseId);
    }
}