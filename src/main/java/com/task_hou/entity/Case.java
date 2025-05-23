package com.task_hou.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("cases")
public class Case {
    @TableId(type = IdType.AUTO)
    private Integer caseId;       // 案例唯一标识（自增主键）
    private String title;         // 案例标题（非空）
    private String category;      // 案例分类（非空）
    private String content;       // 案例详细内容（非空）
    private String solution;      // 解决方案（可为空）
    private String impactAnalysis;// 影响分析（可为空）
    private Integer viewCount;    // 浏览次数（默认0）
    private String imageUrls;     // 图片URL（逗号分隔）
    private Integer adminId;      // 管理员ID（关联admins表）
    private LocalDateTime createTime; // 创建时间（默认当前时间）
    private LocalDateTime updateTime; // 更新时间（自动更新）
}