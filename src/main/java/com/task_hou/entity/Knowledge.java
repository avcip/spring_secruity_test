package com.task_hou.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("knowledge")
public class Knowledge {
    @TableId(type = IdType.AUTO) // 自增主键，对应 SQL 的 knowledge_id
    private Integer knowledgeId;
    private String topic;        // 对应 SQL 的 topic（非空，用于分类）
    private String content;      // 对应 SQL 的 content（text，富文本）
    private Integer viewCount;   // 对应 SQL 的 view_count（默认 0）
    private String imageUrls;    // 对应 SQL 的 image_urls（逗号分隔的图片 URL）
    private Long adminId;     // 原类型为Integer，改为Long
    private LocalDateTime createTime; // 对应 SQL 的 create_time（timestamp）
    private LocalDateTime updateTime; // 对应 SQL 的 update_time（自动更新 timestamp）
}