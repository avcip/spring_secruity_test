package com.task_hou.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.task_hou.entity.UserFeedback;

public interface UserFeedbackService {

    /**
     * 提交用户反馈
     * @param feedback 反馈实体（需包含userId和feedbackContent）
     * @return 保存后的反馈实体
     */
    UserFeedback submitFeedback(UserFeedback feedback);

    /**
     * 管理员分页查询反馈列表
     * @param status 状态筛选（可选）
     * @param page 页码
     * @param pageSize 每页数量
     * @return 分页结果
     */
    IPage<UserFeedback> getFeedbackList(String status, Integer page, Integer pageSize);
}