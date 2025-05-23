package com.task_hou.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.task_hou.common.CommonResult;
import com.task_hou.entity.Admin;
import com.task_hou.entity.ExpertApplication;
import com.task_hou.entity.User;
import com.task_hou.service.AdminService;
import com.task_hou.service.ExpertService;
import com.task_hou.service.UserService;
import com.task_hou.utils.JwtUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserService userService;  // 新增用户服务注入
    @Autowired
    private ExpertService expertService;  // 新增专家服务注入

    // 管理员登录（修改后返回token）
    @PostMapping("/admins/login")
    public CommonResult<String> adminLogin(@RequestBody Admin admin) {
        try {
            Admin loggedAdmin = adminService.login(admin.getUsername(), admin.getPassword());
            // 生成管理员token（使用用户名作为JWT的subject）
            String token = jwtUtils.generateToken(loggedAdmin.getUsername());
            return CommonResult.success(token);  // 返回token替代Admin对象
        } catch (IllegalArgumentException e) {
            return CommonResult.error(400, e.getMessage());
        }
    }

    /**
     * 管理员获取所有用户列表（分页）
     * @param page 页码（默认1）
     * @param pageSize 每页数量（默认10）
     * @return 用户列表分页数据（MyBatis-Plus 标准 IPage 类型）
     */
    @GetMapping("/admins/users")
    public CommonResult<IPage<User>> getAllUsers(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        // 使用 MyBatis-Plus 的 Page 对象传递分页参数
        Page<User> pageParam = new Page<>(page, pageSize);
        // 调用 IService 提供的 page 方法（返回 IPage<User>）
        IPage<User> userPage = userService.page(pageParam);
        return CommonResult.success(userPage);
    }

    /**
     * 管理员获取所有专家申请列表（分页）
     * @param page 页码（默认1）
     * @param pageSize 每页数量（默认10）
     * @return 专家申请列表分页数据
     */
    @GetMapping("/admins/expert-applications")
    public CommonResult<IPage<ExpertApplication>> getAllExpertApplications(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        // 使用 MyBatis-Plus 的 Page 对象传递分页参数
        Page<ExpertApplication> pageParam = new Page<>(page, pageSize);
        IPage<ExpertApplication> applicationPage = expertService.page(pageParam);
        return CommonResult.success(applicationPage);
    }
}