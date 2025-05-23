package com.task_hou.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("user_feedback")
public class UserFeedback {
    @TableId(type = IdType.AUTO)
    private Integer feedbackId;      // 反馈唯一标识（自增主键）
    private Integer userId;          // 用户ID（关联users表，非空）
    private String feedbackContent;  // 反馈内容（非空）
    private LocalDateTime submissionTime; // 提交时间（默认当前时间）
    private String processingStatus; // 处理状态（pending/processing/resolved/rejected）
    private Integer adminId;         // 管理员ID（关联admins表，可为空）
    private String adminReply;       // 管理员回复（可为空）
    private LocalDateTime replyTime; // 回复时间（可为空）
}