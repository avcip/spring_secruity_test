package com.task_hou.controller;

import com.task_hou.common.CommonResult;
import com.task_hou.entity.ExpertApplication; // 需补充专家申请实体类（包含用户ID、申请理由等）
import com.task_hou.service.CaptchaService;
import com.task_hou.service.ExpertService; // 需补充 ExpertService 接口及实现
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ExpertController {
    @Autowired
    private ExpertService expertService;

    // 审批专家申请（管理员权限，拦截器已校验权限）
    @PutMapping("/experts/applications/{application_id}")
    public CommonResult<Void> approveExpertApplication(
        @PathVariable Long application_id,
        @RequestBody ExpertApplication approvalInfo
        // 移除@RequestHeader("Authorization") String token参数，因权限校验已由拦截器完成
    ) {
        try {
            // 直接调用Service业务方法，无需传递token
            expertService.approveApplication(application_id, approvalInfo);
            return CommonResult.success(null);
        } catch (IllegalArgumentException e) {
            return CommonResult.error(400, e.getMessage()); // 仅保留参数错误等业务异常
        }
    }
}