package com.task_hou.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.task_hou.entity.AnswerRecord;
import com.task_hou.entity.Quiz;
import java.util.List;

public interface QuizService {
    /**
     * 获取测验题目列表（按难度筛选）
     * @param difficulty 难度（可选）
     * @return 题目数组
     */
    List<Quiz> getQuizList(String difficulty);

    /**
     * 提交答题记录（返回包含答题记录、正确答案和解析的结果）
     * @param userId 用户ID（Long类型）
     * @param record 答题记录实体
     * @return 包含答题记录、正确答案和解析的Map
     */
    java.util.Map<java.lang.String, java.lang.Object> submitAnswerRecord(Long userId, AnswerRecord record);

    /**
     * 获取用户答题记录（分页）
     * @param userId 用户ID（Long类型）
     * @param quizId 题目ID（可选）
     * @param page 当前页码
     * @param pageSize 每页数量
     * @return 分页答题记录
     */
    Page<AnswerRecord> getUserAnswerRecords(Long userId, Integer quizId, Integer page, Integer pageSize);

    /**
     * 根据ID获取单个测验题目
     * @param quizId 题目ID
     * @return 题目信息
     */
    Quiz getQuizById(Integer quizId);
}