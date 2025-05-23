package com.task_hou.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("answer_records")
public class AnswerRecord {
    @TableId(type = IdType.AUTO)
    private Integer answerId;
    private Long userId;     // 注意：根据 JWT 解析结果，建议改为 Long 类型（避免类型不匹配）
    private Integer quizId;
    private Boolean correct; // 0-错误，1-正确
    private Integer score;
    private String userAnswer;  // 新增：用户提交的答案内容
    private LocalDateTime answerTime;
}
