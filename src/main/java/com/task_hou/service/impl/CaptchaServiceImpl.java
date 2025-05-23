package com.task_hou.service.impl;

import com.task_hou.service.CaptchaService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
public class CaptchaServiceImpl implements CaptchaService {
    // 内存缓存（实际生产环境建议用 Redis）
    private final Map<String, String> captchaCache = new ConcurrentHashMap<>();

    @Override
    public void sendRegisterCaptcha(String email) {
        // 生成 6 位随机验证码
        String captcha = String.format("%06d", (int) (Math.random() * 999999));
        
        // 缓存验证码（有效期 5 分钟）
        captchaCache.put(email, captcha);
        new Thread(() -> {
            try {
                TimeUnit.MINUTES.sleep(5);
                captchaCache.remove(email); // 自动过期
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();

        // 模拟发送验证码（实际需调用短信/邮件接口）
        System.out.printf("模拟发送验证码到 %s：%s%n", email, captcha);
    }

    @Override
    public boolean verifyRegisterCaptcha(String email, String inputCaptcha) {
        String cachedCaptcha = captchaCache.get(email);
        return inputCaptcha != null && inputCaptcha.equals(cachedCaptcha);
    }
}