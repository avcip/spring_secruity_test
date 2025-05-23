package com.task_hou.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.task_hou.common.CommonResult;
import com.task_hou.entity.Forum;
import com.task_hou.service.ForumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;

@RestController
public class ForumController {

    @Autowired
    private ForumService forumService;

    // 11.1 发布新帖子（POST /forums）
    @PostMapping("/forums")
    public CommonResult<Forum> postForum(
            HttpServletRequest request,
            @RequestBody Forum forum
    ) {
        // 从拦截器获取登录用户ID（拦截器已完成登录验证）
        Object userIdObj = request.getAttribute("userId");
        if (userIdObj == null) {
            return CommonResult.error(401, "未登录或令牌无效");
        }
        Integer userId = Integer.parseInt(userIdObj.toString());

        // 调用Service发布帖子
        Forum savedForum = forumService.postForum(forum, userId);
        return CommonResult.success(savedForum);
    }

    // 11.2 获取帖子列表（GET /forums）
    @GetMapping("/forums")
    public CommonResult<IPage<Forum>> getForumList(
            @RequestParam(required = false) String topic,  // 可选查询参数
            @RequestParam(required = false) Integer page,   // 默认1
            @RequestParam(required = false) Integer pageSize // 默认10
    ) {
        // 调用Service获取分页列表
        IPage<Forum> forumPage = forumService.getForumList(topic, page, pageSize);
        return CommonResult.success(forumPage);
    }
}