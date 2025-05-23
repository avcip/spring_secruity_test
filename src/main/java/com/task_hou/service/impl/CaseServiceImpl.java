package com.task_hou.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.task_hou.entity.Case;
import com.task_hou.mapper.CaseMapper;
import com.task_hou.service.CaseService;
import org.springframework.stereotype.Service;
// 新增：导入获取管理员信息的工具类（根据实际项目调整）
import com.task_hou.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class CaseServiceImpl extends ServiceImpl<CaseMapper, Case> implements CaseService {

    // 新增：注入 JWT 工具类（假设项目中已存在）
    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public Case createCase(Case caseInfo) {
        // 直接使用Controller传递的adminId（已通过拦截器校验）
        save(caseInfo);
        return caseInfo;
    }

    @Override
    public IPage<Case> getCaseList(Integer page, Integer pageSize, String category) {
        Page<Case> pageParam = new Page<>(page == null ? 1 : page, pageSize == null ? 10 : pageSize);
        return baseMapper.selectByCategory(pageParam, category);
    }
}
