package com.task_hou.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("comments")
public class Comment {
    @TableId(type = IdType.AUTO)
    private Integer commentId;     // 评论唯一标识（自增主键）
    private Integer userId;        // 用户ID（关联users表，非空）
    private Integer forumId;       // 帖子ID（关联forums表，可为空）
    private Integer knowledgeId;   // 知识ID（关联knowledge表，可为空）
    private Integer caseId;        // 案例ID（关联cases表，可为空）
    private Integer parentId;      // 父评论ID（嵌套评论）
    private String commentContent; // 评论内容（非空）
    private LocalDateTime commentTime; // 评论时间（默认当前时间）
    private Integer likeCount;     // 点赞数（默认0）
    private String status;         // 状态（active/deleted，默认active）
}