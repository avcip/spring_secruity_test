package com.task_hou.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.task_hou.entity.User;

import java.util.Map;

// 继承IService<User>以获得基础CRUD方法声明
public interface UserService extends IService<User> {
    /**
     * 用户注册
     * @param user 注册用户信息（包含username/password/email/captcha）
     * @return 注册成功的用户信息
     */
    User register(User user);

    /**
     * 用户登录（返回 JWT 令牌）
     * @param email 登录邮箱
     * @param password 登录密码
     * @return JWT 令牌字符串
     */
    String login(String email, String password); // 修改返回类型为 String

    /**
     * 获取当前用户信息（需补充实现）
     * @return 当前用户信息
     */
    User getCurrentUser(String token);

    /**
     * 更新用户资料（需补充实现）
     * @param user 更新的用户信息（username/bio/avatarUrl）
     * @return 更新后的用户信息
     */
    User updateUser(User user);

    User getUserByEmail(String email);

    User getById(Long userId);

    /**
     * 根据用户ID获取用户信息
     * @param userId 用户ID
     * @return 用户实体
     */
    // 新增：根据用户 ID 获取用户信息
    User getCurrentUser(Long userId);

    /**
     * 获取用户收藏内容（分页）
     * @param userId 用户ID
     * @param type 收藏类型（可选，knowledge/case/forum）
     * @param page 页码
     * @param pageSize 每页数量
     * @return 收藏内容分页信息
     */
    PageInfo<Map<String, Object>> getUserFavorites(Long userId, String type, int page, int pageSize);

    /**
     * 获取用户收藏的知识内容（分页）
     * @param userId 用户ID
     * @param page 页码
     * @param pageSize 每页数量
     * @return 知识收藏分页信息
     */
    PageInfo<Map<String, Object>> getUserKnowledgeFavorites(Long userId, int page, int pageSize);

    /**
     * 获取用户收藏的案例内容（分页）
     * @param userId 用户ID
     * @param page 页码
     * @param pageSize 每页数量
     * @return 案例收藏分页信息
     */
    PageInfo<Map<String, Object>> getUserCaseFavorites(Long userId, int page, int pageSize);

    /**
     * 获取用户收藏的论坛内容（分页）
     * @param userId 用户ID
     * @param page 页码
     * @param pageSize 每页数量
     * @return 论坛收藏分页信息
     */
    PageInfo<Map<String, Object>> getUserForumFavorites(Long userId, int page, int pageSize);
}