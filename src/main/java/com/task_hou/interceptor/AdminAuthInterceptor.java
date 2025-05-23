package com.task_hou.interceptor;

import com.task_hou.entity.Admin;
import com.task_hou.service.AdminService;
import com.task_hou.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AdminAuthInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private AdminService adminService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        System.out.println(888);
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(401);
            try {
                // 新增try-catch处理IOException
                response.getWriter().write("{\"code\":401,\"message\":\"1111\"}");
            } catch (java.io.IOException e) {
                System.err.println("写入响应失败: " + e.getMessage());
            }
            return false;
        }
        String token = authHeader.substring(7);
        try {
            Claims claims = jwtUtils.parseToken(token);
            String adminUsername = claims.getSubject();
            System.out.println("拦截到管理员请求，解析的用户名: " + adminUsername);

            Admin admin = adminService.getAdminByUsername(adminUsername);
            if (admin == null) {
                response.setStatus(401);
                try {
                    // 新增try-catch处理IOException
                    response.getWriter().write("{\"code\":401,\"message\":\"12356\"}");
                } catch (java.io.IOException e) {
                    System.err.println("写入响应失败: " + e.getMessage());
                }
                return false;
            }
            System.out.println("管理员权限校验通过，用户名: " + adminUsername);
            // 新增：将管理员ID保存到请求属性（假设Admin实体有adminId字段）
            request.setAttribute("adminId", admin.getAdminId()); 
            return true;
        } catch (Exception e) {
            response.setStatus(401);
            try {
                // 新增try-catch处理IOException
                response.getWriter().write("{\"code\":401,\"message\":\"111111: " + e.getMessage() + "\"}");
            } catch (java.io.IOException ioException) {
                System.err.println("写入响应失败: " + ioException.getMessage());
            }
            return false;
        }
    }
}