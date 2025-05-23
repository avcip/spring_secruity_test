package com.task_hou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.task_hou.entity.UserFeedback;
import com.task_hou.mapper.UserFeedbackMapper;
import com.task_hou.service.UserFeedbackService;
import org.springframework.stereotype.Service;

@Service
public class UserFeedbackServiceImpl extends ServiceImpl<UserFeedbackMapper, UserFeedback> implements UserFeedbackService {

    @Override
    public UserFeedback submitFeedback(UserFeedback feedback) {
        // 保存反馈（MyBatis-Plus的save方法）
        save(feedback);
        return feedback;
    }

    @Override
    public IPage<UserFeedback> getFeedbackList(String status, Integer page, Integer pageSize) {
        // 构造分页参数
        Page<UserFeedback> pageParam = new Page<>(page, pageSize);
        // 构造查询条件
        QueryWrapper<UserFeedback> queryWrapper = new QueryWrapper<>();
        if (status != null && !status.isEmpty()) {
            queryWrapper.eq("processing_status", status);
        }
        // 执行分页查询
        return page(pageParam, queryWrapper);
    }
}