package com.task_hou.service;

import com.task_hou.entity.Admin;

public interface AdminService {
    /**
     * 管理员登录验证
     * @param username 管理员用户名
     * @param password 管理员密码（明文）
     * @return 登录成功的 Admin 对象
     * @throws IllegalArgumentException 用户名不存在或密码错误时抛出
     */
    Admin login(String username, String password);

    Admin getAdminByUsername(String adminUsername);
}