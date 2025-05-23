package com.task_hou.service;

// 改为 MyBatis-Plus 的 IPage
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.task_hou.entity.Case;

public interface CaseService {
    Case createCase(Case caseInfo);
    // 修正返回类型为 MyBatis-Plus 的 IPage
    IPage<Case> getCaseList(Integer page, Integer pageSize, String category);
}
