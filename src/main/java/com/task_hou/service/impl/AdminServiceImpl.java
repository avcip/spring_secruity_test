package com.task_hou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.task_hou.entity.Admin;
import com.task_hou.mapper.AdminMapper;
import com.task_hou.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public Admin login(String username, String password) {
        // 1. 根据用户名查询管理员
        Admin admin = adminMapper.selectOne(new QueryWrapper<Admin>().eq("username", username));
        if (admin == null) {
            throw new IllegalArgumentException("管理员不存在");
        }

        // 2. 验证密码
        if (!passwordEncoder.matches(password, admin.getPassword())) {
            throw new IllegalArgumentException("密码错误");
        }

        // 3. 更新最后登录时间（关键新增逻辑）
        admin.setLastLogin(LocalDateTime.now()); // 设置当前时间
        adminMapper.updateById(admin); // 执行数据库更新

        return admin;
    }

    @Override
    public Admin getAdminByUsername(String username) {
        return adminMapper.selectOne(new QueryWrapper<Admin>().eq("username", username));
    }
}