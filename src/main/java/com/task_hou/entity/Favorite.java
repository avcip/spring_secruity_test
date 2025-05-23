package com.task_hou.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("favorites")
public class    Favorite {
    @TableId(type = IdType.AUTO)
    private Integer favoriteId;   // 收藏记录唯一标识（自增主键）
    private Integer userId;       // 用户ID（关联users表，非空）
    private Integer knowledgeId;  // 知识ID（关联knowledge表，可为空）
    private Integer caseId;       // 案例ID（关联cases表，可为空）
    private Integer forumId;      // 帖子ID（关联forums表，可为空）
    private LocalDateTime createTime; // 收藏时间（默认当前时间）
}