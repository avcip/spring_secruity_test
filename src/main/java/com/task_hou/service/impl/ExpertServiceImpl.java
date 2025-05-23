package com.task_hou.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.task_hou.entity.Admin;
import com.task_hou.entity.ExpertApplication;
import com.task_hou.mapper.ExpertApplicationMapper;
import com.task_hou.service.AdminService;
import com.task_hou.service.ExpertService;
import com.task_hou.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpertServiceImpl implements ExpertService {
    @Autowired
    private ExpertApplicationMapper expertApplicationMapper;

    @Override
    public void approveApplication(Long applicationId, ExpertApplication approvalInfo) { // 移除token参数
        // 仅保留业务逻辑（更新申请状态）
        UpdateWrapper<ExpertApplication> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("application_id", applicationId)
                     .set("approval_status", approvalInfo.getApprovalStatus())
                     .set("approval_reason", approvalInfo.getApprovalReason());
        expertApplicationMapper.update(null, updateWrapper);
    }

    // 实现分页方法
    @Override
    public IPage<ExpertApplication> page(Page<ExpertApplication> pageParam) {
        return expertApplicationMapper.selectPage(pageParam, new QueryWrapper<>());
    }
}