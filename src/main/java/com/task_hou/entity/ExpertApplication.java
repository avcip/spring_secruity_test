package com.task_hou.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("expert_application")
public class ExpertApplication {
    @TableId(type = IdType.AUTO) // 自增主键，对应 SQL 的 application_id
    private Integer applicationId;
    private Integer userId;               // 对应 SQL 的 user_id（关联 users 表）
    private LocalDateTime applicationTime; // 对应 SQL 的 application_time（timestamp）
    private String applicationContent;    // 对应 SQL 的 application_content（text）
    private String approvalStatus;        // 对应 SQL 的 approval_status（enum: pending/approved/rejected）
    private LocalDateTime approvalTime;   // 对应 SQL 的 approval_time（timestamp）
    private String approvalReason;        // 对应 SQL 的 approval_reason（text）
}