package com.task_hou.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.task_hou.common.CommonResult;
import com.task_hou.entity.UserFeedback;
import com.task_hou.service.UserFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class UserFeedbackController {

    @Autowired
    private UserFeedbackService userFeedbackService;

    // 12.1 提交用户反馈（登录用户权限）
    @PostMapping("/feedback")
    public CommonResult<UserFeedback> submitFeedback(
            HttpServletRequest request,
            @RequestBody Map<String, String> requestBody) {
        // 从拦截器获取登录用户ID
        Object userIdObj = request.getAttribute("userId");
        if (userIdObj == null) {
            return CommonResult.error(401, "未登录或令牌无效");
        }
        Integer userId = Integer.parseInt(userIdObj.toString());

        // 构造反馈对象
        UserFeedback feedback = new UserFeedback();
        feedback.setUserId(userId);
        feedback.setFeedbackContent(requestBody.get("feedback_content"));
        feedback.setSubmissionTime(java.time.LocalDateTime.now());
        feedback.setProcessingStatus("pending"); // 默认状态为pending

        // 调用Service保存
        UserFeedback savedFeedback = userFeedbackService.submitFeedback(feedback);
        return CommonResult.success(savedFeedback);
    }

    // 12.2 管理员获取所有反馈（管理员权限）
    @GetMapping("/admins/feedback")
    public CommonResult<Map<String, Object>> getFeedbackList(
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer page_size) {

        // 调用Service获取分页数据
        IPage<UserFeedback> feedbackPage = userFeedbackService.getFeedbackList(status, page, page_size);

        // 转换为要求的响应格式
        Map<String, Object> result = new HashMap<>();
        result.put("total", feedbackPage.getTotal());
        result.put("list", feedbackPage.getRecords().stream().map(f -> {
            Map<String, Object> item = new HashMap<>();
            item.put("feedback_id", f.getFeedbackId());
            item.put("user_id", f.getUserId());
            item.put("feedback_content", f.getFeedbackContent());
            item.put("processing_status", f.getProcessingStatus());
            item.put("submission_time", f.getSubmissionTime());
            return item;
        }).collect(Collectors.toList()));

        return CommonResult.success(result);
    }
}