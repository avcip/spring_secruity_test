package com.task_hou.interceptor;

import com.task_hou.common.CommonResult;
import com.task_hou.entity.Admin;
import com.task_hou.entity.User;
import com.task_hou.service.AdminService;
import com.task_hou.service.UserService;
import com.task_hou.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserService userService;
    @Autowired
    private AdminService adminService;  // 新增：注入AdminService用于查询管理员

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        if (token == null) {
            response.setStatus(401);
            response.getWriter().write("{\"code\":401,\"message\":\"未登录\",\"error\":\"缺少Authorization令牌\"}");
            return false;
        }

        try {
            String actualToken = token.startsWith("Bearer ") ? token.substring(7) : token;
            Claims claims = jwtUtils.parseToken(actualToken);
            String subject = claims.getSubject();  // 用户token的subject是用户ID（数字），管理员token的是用户名（非数字）

            // 尝试解析为用户ID（用户token）
            try {
                Long userId = Long.parseLong(subject);
                User user = userService.getById(userId);
                if (user == null) {
                    response.setStatus(401);
                    response.getWriter().write("{\"code\":401,\"message\":\"用户不存在\"}");
                    return false;
                }

                // 普通用户权限校验（保持原有逻辑）
                request.setAttribute("userId", userId);
                if (request.getRequestURI().equals("/knowledge/create") && "POST".equals(request.getMethod())) {
                    if (!user.getIsExpert()) {
                        response.setStatus(403);
                        response.getWriter().write("{\"code\":403,\"message\":\"无权限\",\"error\":\"需要专家身份\"}");
                        return false;
                    }
                }
                return true;
            } catch (NumberFormatException e) {
                // 解析失败，尝试作为管理员用户名（管理员token）
                Admin admin = adminService.getAdminByUsername(subject);
                if (admin == null) {
                    response.setStatus(401);
                    response.getWriter().write("{\"code\":401,\"message\":\"管理员不存在\"}");
                    return false;
                }

                // 管理员验证通过，放行用户权限接口
                request.setAttribute("adminId", admin.getAdminId());  // 传递管理员ID供后续使用
                return true;
            }
        } catch (Exception e) {
            response.setStatus(401);
            response.getWriter().write("{\"code\":401,\"message\":\"令牌无效或过期\"}");
            return false;
        }
    }
}