package com.task_hou.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("forums")
public class Forum {
    @TableId(type = IdType.AUTO)
    private Integer forumId;       // 帖子唯一标识（自增主键）
    private String topic;          // 帖子主题（非空）
    private String postContent;    // 帖子内容（非空）
    private Integer userId;        // 用户ID（关联users表，非空）
    private LocalDateTime postTime; // 发帖时间（默认当前时间）
    private Integer likeCount;     // 点赞数（默认0）
    private Integer commentCount;  // 评论数（默认0）
    private Integer viewCount;     // 浏览次数（默认0）
    private String imageUrls;      // 图片URL（逗号分隔）
}