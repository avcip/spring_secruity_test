package com.task_hou.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.task_hou.entity.Forum;

public interface ForumService {

    /**
     * 发布新帖子（登录用户）
     * @param forum 请求体中的帖子信息（topic、postContent、imageUrls）
     * @param userId 登录用户ID（来自拦截器）
     * @return 发布成功的帖子信息
     */
    Forum postForum(Forum forum, Integer userId);

    /**
     * 获取帖子列表（分页）
     * @param topic 主题关键词（可选）
     * @param page 页码（默认1）
     * @param pageSize 每页数量（默认10）
     * @return 分页结果（包含total和list）
     */
    IPage<Forum> getForumList(String topic, Integer page, Integer pageSize);
}