package com.task_hou.controller;

import com.task_hou.common.CommonResult;
import com.task_hou.entity.User;
import com.task_hou.service.CaptchaService;
import com.task_hou.service.UserService;
import com.task_hou.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

import com.github.pagehelper.PageInfo;  // 假设使用PageHelper分页（需根据实际分页工具调整）
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;
import com.task_hou.entity.Knowledge;
import com.task_hou.service.KnowledgeService;  // 新增导入

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtils jwtUtils;  // 注入 JWT 工具类
    @Autowired
    private KnowledgeService knowledgeService;  // 新增知识服务注入

    // 用户注册（保留包含异常处理的版本）
    @PostMapping("/users/register")
    public CommonResult<User> register(@RequestBody User user) {
        try {
            User registeredUser = userService.register(user);
            return CommonResult.success(registeredUser);
        } catch (IllegalArgumentException e) {
            return CommonResult.error(400, e.getMessage());
        }
    }

    // 用户登录
    @PostMapping("/users/login")
    public CommonResult<String> login(@RequestBody User user) {
        try {
            String token = userService.login(user.getEmail(), user.getPassword());
            return CommonResult.success(token);
        } catch (IllegalArgumentException e) {
            return CommonResult.error(400, e.getMessage());
        }
    }

    // 新增：获取当前用户信息
    @GetMapping("/users/me")
    public CommonResult<User> getCurrentUser(HttpServletRequest request) {
        // 1. 从请求头获取 JWT 令牌（格式：Bearer <token>）
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return CommonResult.error(401, "未登录或令牌格式错误");
        }
        String token = authHeader.substring(7);  // 去掉 "Bearer " 前缀

        // 2. 解析令牌获取用户 ID
        try {
            Claims claims = jwtUtils.parseToken(token);
            Long userId = Long.parseLong(claims.getSubject());  // 令牌的 subject 是用户 ID

            // 3. 查询用户信息
            User user = userService.getCurrentUser(userId);
            if (user == null) {
                return CommonResult.error(404, "用户不存在");
            }

            // 4. 过滤敏感字段（如密码）
            user.setPassword(null);
            return CommonResult.success(user);
        } catch (Exception e) {
            return CommonResult.error(401, "无效的令牌：" + e.getMessage());
        }
    }

    // 新增：更新当前用户资料
    @PutMapping("/users/me")
    public CommonResult<User> updateCurrentUser(HttpServletRequest request, @RequestBody User updateInfo) {
        // 1. 从请求头获取 JWT 令牌
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return CommonResult.error(401, "未登录或令牌格式错误");
        }
        String token = authHeader.substring(7);

        // 2. 解析令牌获取用户 ID
        try {
            Claims claims = jwtUtils.parseToken(token);
            Long userId = Long.parseLong(claims.getSubject());

            // 3. 校验更新参数（示例：username长度）
            if (updateInfo.getUsername() != null && (updateInfo.getUsername().length() < 3 || updateInfo.getUsername().length() > 50)) {
                return CommonResult.error(400, "用户名长度需在3-50字符之间");
            }

            // 4. 构造更新对象
            User user = new User();
            user.setUserId(userId);
            user.setUsername(updateInfo.getUsername());
            user.setBio(updateInfo.getBio());
            user.setAvatarUrl(updateInfo.getAvatarUrl());

            // 5. 调用Service更新
            User updatedUser = userService.updateUser(user);
            updatedUser.setPassword(null); // 过滤敏感信息
            return CommonResult.success(updatedUser);
        } catch (Exception e) {
            return CommonResult.error(401, "无效的令牌：" + e.getMessage());
        }
    }

    // 新增：获取用户收藏内容
    @GetMapping("/users/me/favorites")
    public CommonResult<Map<String, Object>> getUserFavorites(
            HttpServletRequest request,
            @RequestParam(required = false) String type,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        // 1. 从请求属性中获取用户ID（由拦截器注入）
        Object userIdObj = request.getAttribute("userId");
        if (userIdObj == null) {
            return CommonResult.error(401, "未登录或令牌无效");
        }
        Long userId = Long.parseLong(userIdObj.toString());

        // 2. 校验type参数有效性（可选）
        if (type != null && !Arrays.asList("knowledge", "case", "forum").contains(type)) {
            return CommonResult.error(400, "type参数无效，可选值：knowledge/case/forum");
        }

        // 3. 调用Service获取收藏数据（逻辑不变）
        PageInfo<Map<String, Object>> favoritesPage;
        switch (type) {
            case "knowledge":
                favoritesPage = userService.getUserKnowledgeFavorites(userId, page, pageSize);
                break;
            case "case":
                favoritesPage = userService.getUserCaseFavorites(userId, page, pageSize);
                break;
            case "forum":
                favoritesPage = userService.getUserForumFavorites(userId, page, pageSize);
                break;
            default:
                favoritesPage = userService.getUserFavorites(userId, null, page, pageSize);
                break;
        }

        // 4. 构造响应体（逻辑不变）
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("total", favoritesPage.getTotal());
        responseData.put("list", favoritesPage.getList());

        return CommonResult.success(responseData);
    }


}