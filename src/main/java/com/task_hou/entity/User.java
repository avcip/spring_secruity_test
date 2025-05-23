package com.task_hou.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("users")
public class User {
    @TableId(type = IdType.AUTO) // 自增主键，对应 SQL 的 user_id
    // 修改字段类型为 Long（原 Integer）
    private Long userId;
    private String username;    // 对应 SQL 的 username（非空）
    private String password;    // 对应 SQL 的 password（非空）
    private String email;       // 对应 SQL 的 email（非空且唯一）
    private String avatarUrl;   // 对应 SQL 的 avatar_url（默认 default_avatar.png）
    private String bio;         // 对应 SQL 的 bio（用户简介，text）
    private Boolean isExpert;   // 对应 SQL 的 is_expert（0-普通用户，1-专家）
    private LocalDateTime createTime; // 对应 SQL 的 create_time（timestamp）
    private LocalDateTime updateTime; // 对应 SQL 的 update_time（自动更新 timestamp）
}