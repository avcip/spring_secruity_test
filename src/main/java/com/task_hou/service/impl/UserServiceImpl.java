package com.task_hou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.task_hou.entity.User;
import com.task_hou.mapper.CaseMapper;
import com.task_hou.mapper.ForumMapper;
import com.task_hou.mapper.KnowledgeMapper;
import com.task_hou.mapper.UserMapper;
import com.task_hou.service.UserService;
import com.task_hou.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
// 新增继承关系：ServiceImpl<Mapper接口, 实体类>
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserMapper userMapper;
    // 新增 Mapper 注入（需确保这些 Mapper 已定义）
    @Autowired
    private KnowledgeMapper knowledgeMapper;
    @Autowired
    private CaseMapper caseMapper;
    @Autowired
    private ForumMapper forumMapper;

    @Autowired
    private JwtUtils jwtUtils;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @Override
    public User getCurrentUser(String token) { // 接收 Controller 传递的令牌
        // 解析令牌获取用户 ID
        Claims claims = jwtUtils.parseToken(token);
        Long userId = Long.parseLong(claims.getSubject());
    
        // 查询用户信息
        return getById(userId);
    }

    @Override
    public User updateUser(User user) {
        // 仅允许更新用户名、简介、头像（根据接口文档选填字段）
        User updateUser = new User();
        updateUser.setUserId(user.getUserId());
        updateUser.setUsername(user.getUsername());
        updateUser.setBio(user.getBio());
        updateUser.setAvatarUrl(user.getAvatarUrl());

        // 执行更新（继承自ServiceImpl的updateById方法）
        updateById(updateUser);  // 现在可以正常调用

        // 返回更新后的用户信息
        return getById(user.getUserId());
    }

    @Override
    public String login(String email, String password) {
        // 1. 根据邮箱查询用户
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("email", email));
        
        // 2. 验证用户是否存在
        if (user == null) {
            throw new IllegalArgumentException("用户不存在");
        }
        
        // 3. 验证密码
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("密码错误");
        }
        
        // 4. 生成 JWT 令牌（直接返回，不设置到 User 对象）
        return jwtUtils.generateToken(user.getUserId()); 
    }

    @Override
    public User register(User user) {
        // 1. 校验邮箱格式（示例简化，实际需正则）
        if (!user.getEmail().contains("@")) {
            throw new IllegalArgumentException("邮箱格式错误");
        }
    
        // 2. 检查用户名是否已存在
        User existUser = userMapper.selectOne(new QueryWrapper<User>().eq("username", user.getUsername()));
        if (existUser != null) {
            throw new IllegalArgumentException("用户名已存在");
        }
    
        // 3. 加密密码
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    
        // 4. 保存用户
        userMapper.insert(user);
    
        return user;
    }

    @Override
    public User getUserByEmail(String email) {
        return userMapper.selectOne(new QueryWrapper<User>().eq("email", email));
    }

    @Override
    public User getById(Long userId) {  // 修改方法名为 getById（原 getUserById）
        return userMapper.selectById(userId);
    }

    // 新增：根据用户 ID 获取用户信息
    @Override
    public User getCurrentUser(Long userId) {
        return userMapper.selectById(userId);
    }

    @Override
    public PageInfo<Map<String, Object>> getUserFavorites(Long userId, String type, int page, int pageSize) {
        // 示例：假设收藏数据存储在 user_favorite 表中（需根据实际表结构调整）
        PageHelper.startPage(page, pageSize);
        List<Map<String, Object>> favorites = userMapper.getUserFavorites(userId, type); // 需在 UserMapper 中添加对应 SQL
        return new PageInfo<>(favorites);
    }

    @Override
    public PageInfo<Map<String, Object>> getUserKnowledgeFavorites(Long userId, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<Map<String, Object>> knowledgeFavorites = knowledgeMapper.getUserKnowledgeFavorites(userId);
        return new PageInfo<>(knowledgeFavorites);
    }

    @Override
    public PageInfo<Map<String, Object>> getUserCaseFavorites(Long userId, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<Map<String, Object>> caseFavorites = caseMapper.getUserCaseFavorites(userId);
        return new PageInfo<>(caseFavorites);
    }

    @Override
    public PageInfo<Map<String, Object>> getUserForumFavorites(Long userId, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<Map<String, Object>> forumFavorites = forumMapper.getUserForumFavorites(userId);
        return new PageInfo<>(forumFavorites);
    }
}