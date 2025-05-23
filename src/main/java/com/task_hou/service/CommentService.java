package com.task_hou.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.task_hou.entity.Comment;

public interface CommentService {

    /**
     * 发布评论（自动填充默认值）
     * @param comment 评论实体（需包含关联资源ID和内容）
     * @return 保存后的评论实体
     */
    Comment postComment(Comment comment);

    /**
     * 根据关联资源ID分页查询评论
     * @param forumId 论坛ID（可选）
     * @param knowledgeId 知识ID（可选）
     * @param caseId 案例ID（可选）
     * @param page 当前页码
     * @param pageSize 每页数量
     * @return 分页评论列表
     */
    IPage<Comment> getCommentList(
        Integer forumId,
        Integer knowledgeId,
        Integer caseId,
        Integer page,
        Integer pageSize
    );
}