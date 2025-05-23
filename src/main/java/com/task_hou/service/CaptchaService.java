package com.task_hou.service;

public interface CaptchaService {
    /**
     * 发送注册验证码（短信/邮件）
     * @param email 用户邮箱（或手机号）
     */
    void sendRegisterCaptcha(String email);

    /**
     * 校验注册验证码
     * @param email 用户邮箱（或手机号）
     * @param inputCaptcha 用户输入的验证码
     * @return 校验是否通过
     */
    boolean verifyRegisterCaptcha(String email, String inputCaptcha);
}