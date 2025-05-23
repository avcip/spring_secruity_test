package com.task_hou.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("admins") // 与 SQL 表名一致
public class Admin {
    @TableId(type = IdType.AUTO) // 自增主键，对应 SQL 的 admin_id
    private Integer adminId;
    private String username; // 对应 SQL 的 username（非空）
    private String password; // 对应 SQL 的 password（非空）
    private String email;    // 对应 SQL 的 email（非空且唯一）
    private LocalDateTime createTime; // 对应 SQL 的 create_time（timestamp）
    private LocalDateTime lastLogin;  // 对应 SQL 的 last_login（timestamp）
}