package com.task_hou.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.task_hou.common.CommonResult;
import com.task_hou.entity.Comment;
import com.task_hou.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    // 9.1 发布评论（登录用户权限）
    @PostMapping("/comments")
    public CommonResult<Comment> postComment(
            HttpServletRequest request,
            @RequestBody Comment commentInfo) {
        // 从拦截器注入的请求属性中获取用户ID（假设拦截器已处理）
        Object userIdObj = request.getAttribute("userId");
        if (userIdObj == null) {
            return CommonResult.error(401, "未登录或令牌无效");
        }
        Long userId = Long.parseLong(userIdObj.toString());

        // 设置用户ID到评论对象
        commentInfo.setUserId(Math.toIntExact(userId));
        // 调用Service保存评论
        Comment savedComment = commentService.postComment(commentInfo);
        return CommonResult.success(savedComment);
    }

    // 9.2 获取评论列表（支持关联资源筛选和分页）
    @GetMapping("/comments")
    public CommonResult<Map<String, Object>> getCommentList(
            @RequestParam(required = false) Integer forum_id,
            @RequestParam(required = false) Integer knowledge_id,
            @RequestParam(required = false) Integer case_id,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer page_size) {

        // 调用Service获取分页数据（参数为可选，Service需处理动态查询）
        IPage<Comment> commentPage = commentService.getCommentList(
                forum_id, knowledge_id, case_id, page, page_size);

        // 转换为要求的响应格式
        Map<String, Object> result = new HashMap<>();
        result.put("total", commentPage.getTotal());
        result.put("list", commentPage.getRecords().stream().map(c -> {
            Map<String, Object> item = new HashMap<>();
            item.put("comment_id", c.getCommentId());
            item.put("user_id", c.getUserId());
            item.put("comment_content", c.getCommentContent());
            item.put("like_count", c.getLikeCount());
            item.put("comment_time", c.getCommentTime());
            return item;
        }).collect(Collectors.toList()));

        return CommonResult.success(result);
    }
}