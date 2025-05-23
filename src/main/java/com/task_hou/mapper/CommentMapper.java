package com.task_hou.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.task_hou.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

    /**
     * 根据关联资源ID分页查询评论（支持论坛/知识/案例评论）
     * @param page 分页参数
     * @param forumId 论坛ID（可选）
     * @param knowledgeId 知识ID（可选）
     * @param caseId 案例ID（可选）
     * @return 分页评论列表
     */
    IPage<Comment> selectByRelatedId(
        Page<Comment> page,
        @Param("forumId") Integer forumId,
        @Param("knowledgeId") Integer knowledgeId,
        @Param("caseId") Integer caseId
    );
}