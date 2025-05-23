package com.task_hou.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.task_hou.entity.ExpertApplication;
import io.jsonwebtoken.Claims;

public interface ExpertService {
    /**
     * 审批专家申请（业务方法，权限已由拦截器校验）
     * @param applicationId 专家申请 ID
     * @param approvalInfo 审批信息（如是否通过、备注）
     */
    void approveApplication(Long applicationId, ExpertApplication approvalInfo); // 移除token参数
    
    // 新增分页方法：使用 MyBatis-Plus 的 IPage 作为返回类型
    IPage<ExpertApplication> page(Page<ExpertApplication> pageParam);
}