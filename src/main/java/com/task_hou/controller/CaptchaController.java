package com.task_hou.controller;

import com.task_hou.common.CommonResult;
import com.task_hou.service.AdminService;
import com.task_hou.service.CaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CaptchaController {
    @Autowired
    private CaptchaService captchaService;

    // 发送注册验证码（短信）
    @PostMapping("/captcha/register")
    public CommonResult<Void> sendRegisterCaptcha(@RequestParam String email) {
        captchaService.sendRegisterCaptcha(email);
        return CommonResult.success(null);
    }
}