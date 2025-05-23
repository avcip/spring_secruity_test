package com.task_hou.interceptor;

import com.task_hou.common.CommonResult;
import com.task_hou.entity.User;
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

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        if (token == null) {
            response.setStatus(401);
            response.getWriter().write("{\"code\":401,\"message\":\"未登录\",\"error\":\"缺少Authorization令牌\"}");
            return false;
        }

        // 解析令牌
        try {
            String actualToken = token.startsWith("Bearer ") ? token.substring(7) : token;
            Claims claims = jwtUtils.parseToken(actualToken);
            Long userId = Long.parseLong(claims.getSubject());
            User user = userService.getById(userId);

            // 新增：将用户ID保存到请求属性中，供后续控制器使用
            request.setAttribute("userId", userId);

            // 校验接口权限（示例：/knowledge POST 需要专家或管理员）
            if (request.getRequestURI().equals("/knowledge/create") && request.getMethod().equals("POST")) {
                if (!user.getIsExpert()) {  // 直接使用 Boolean 类型判断（! 表示“非专家”）
                    response.setStatus(403);
                    response.getWriter().write("{\"code\":403,\"message\":\"无权限\",\"error\":\"需要专家或管理员身份\"}");
                    return false;
                }
            }

            // 移除：原 /cases POST 接口的管理员权限校验（由 AdminAuthInterceptor 处理）

            return true;
        } catch (Exception e) {
            response.setStatus(401);
            response.getWriter().write("{\"code\":401,\"message\":\"令牌无效或过期\"}");
            return false;
        }
    }
}