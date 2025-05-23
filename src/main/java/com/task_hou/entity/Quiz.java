package com.task_hou.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("quizzes")
public class Quiz {
    @TableId(type = IdType.AUTO) // 自增主键，对应 SQL 的 quiz_id
    private Integer quizId;
    private String question;    // 对应 SQL 的 question（text，题目内容）
    private String options;     // 对应 SQL 的 options（JSON 格式选项）
    private String answer;      // 对应 SQL 的 answer（正确答案键）
    private String explanation; // 对应 SQL 的 explanation（答案解析，可为空）
    private String difficulty;  // 对应 SQL 的 difficulty（enum: easy/medium/hard）
    private LocalDateTime createTime; // 对应 SQL 的 create_time（timestamp）
}