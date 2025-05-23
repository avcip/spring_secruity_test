package com.task_hou.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.task_hou.common.CommonResult;
import com.task_hou.entity.Case;
import com.task_hou.service.CaseService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class CaseController {
    @Autowired
    private CaseService caseService;

    // 创建案例（权限已由拦截器校验）
    @PostMapping("/cases")
    public CommonResult<Case> createCase(HttpServletRequest request, @RequestBody Case caseInfo) {
        // 从请求属性中获取管理员ID（由AdminAuthInterceptor注入）
        Integer adminId = (Integer) request.getAttribute("adminId");
        if (adminId == null) {
            return CommonResult.error(401, "未获取到管理员身份信息");
        }
        // 设置管理员ID到Case对象
        caseInfo.setAdminId(adminId);
        Case createdCase = caseService.createCase(caseInfo);
        return CommonResult.success(createdCase);
    }

    // 获取案例列表（支持分类筛选和分页）
    @GetMapping("/cases")
    public CommonResult<Map<String, Object>> getCaseList(
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        IPage<Case> casePage = (IPage<Case>) caseService.getCaseList(page, pageSize, category);
        
        // 转换为要求的响应格式
        Map<String, Object> result = new HashMap<>();
        result.put("total", casePage.getTotal());
        result.put("list", casePage.getRecords().stream().map(c -> {
            Map<String, Object> item = new HashMap<>();
            item.put("case_id", c.getCaseId());
            item.put("title", c.getTitle());
            item.put("category", c.getCategory());
            item.put("view_count", c.getViewCount());
            item.put("create_time", c.getCreateTime());
            return item;
        }).collect(Collectors.toList()));
        
        return CommonResult.success(result);
    }
}
