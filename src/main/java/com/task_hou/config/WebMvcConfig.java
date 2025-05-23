package com.task_hou.config;

import com.task_hou.interceptor.AdminAuthInterceptor;
import com.task_hou.interceptor.AuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final AuthInterceptor authInterceptor;
    private final AdminAuthInterceptor adminAuthInterceptor;  // 新增 Admin 拦截器依赖

    // 修改构造函数，注入两个拦截器
    public WebMvcConfig(AuthInterceptor authInterceptor, AdminAuthInterceptor adminAuthInterceptor) {
        this.authInterceptor = authInterceptor;
        this.adminAuthInterceptor = adminAuthInterceptor;
    }

    // 已有拦截器配置（保留原有逻辑）
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册原有通用权限拦截器（新增 /users/me/favorites 路径）
        registry.addInterceptor(authInterceptor)
                .addPathPatterns(
                    "/answer/records",
                    "/users/me/favorites",
                    "/comments",  // 新增：用户收藏接口需要登录校验
                    "/favorites",
                    "/forums",
                    "/feedback"
                )
                .excludePathPatterns(
                    "/users/register",
                    "/users/login",
                    "/captcha/register",
                    "/**/public/**"
                );

        // 新增 Admin 权限拦截器（专注专家审批接口和案例接口）
        registry.addInterceptor(adminAuthInterceptor)
                .addPathPatterns(
                    "/knowledge/create",
                    "/experts/applications/**",
                    "/cases/**",  // 新增：拦截案例相关接口
                    "/admins/expert-applications",
                    "/admins/users",
                    "/admins/feedback"
                );
    }

    // 跨域配置（保持不变）
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
}