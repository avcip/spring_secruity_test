package com.task_hou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.task_hou.entity.AnswerRecord;
import com.task_hou.entity.Quiz;
import com.task_hou.mapper.AnswerRecordMapper;
import com.task_hou.mapper.QuizMapper;
import com.task_hou.service.QuizService;
import com.task_hou.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QuizServiceImpl implements QuizService {
    @Autowired
    private QuizMapper quizMapper;
    @Autowired
    private AnswerRecordMapper answerRecordMapper;
    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public List<Quiz> getQuizList(String difficulty) {
        QueryWrapper<Quiz> queryWrapper = new QueryWrapper<>();
        // 显式指定返回字段（排除 answer 和 explanation）
        queryWrapper.select("quiz_id", "question", "options", "difficulty", "create_time");
        if (difficulty != null) {
            queryWrapper.eq("difficulty", difficulty);
        }
        return quizMapper.selectList(queryWrapper);
    }

    @Override
    public Quiz getQuizById(Integer quizId) {
        // 显式指定返回字段（排除 answer 和 explanation）
        QueryWrapper<Quiz> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("quiz_id", "question", "options", "difficulty", "create_time");
        queryWrapper.eq("quiz_id", quizId);
        return quizMapper.selectOne(queryWrapper);
    }

    @Override
    public Map<String, Object> submitAnswerRecord(Long userId, AnswerRecord record) {
        // 直接使用传入的userId，无需解析token
        record.setUserId(userId);

        // 新增：设置答题时间为当前时间
        record.setAnswerTime(LocalDateTime.now());

        // 校验答案并设置 correct 字段（0-错误，1-正确）
        Quiz quiz = quizMapper.selectById(record.getQuizId());  // selectById 会返回所有字段（包括 answer 和 explanation）
        boolean isCorrect = quiz.getAnswer().equals(record.getUserAnswer());
        record.setCorrect(isCorrect);  // 设置 correct 字段
        record.setScore(isCorrect ? 100 : 0);  // 根据 correct 设置分数
        System.out.println("666666666666666666");
        answerRecordMapper.insert(record);

        // 构造包含正确答案和解析的返回结果
        Map<String, Object> result = new HashMap<>();
        result.put("answerRecord", record);        // 原始答题记录
        result.put("correctAnswer", quiz.getAnswer());  // 正确答案
        result.put("explanation", quiz.getExplanation());  // 题目解析
        return result;
    }

    @Override
    public Page<AnswerRecord> getUserAnswerRecords(Long userId, Integer quizId, Integer page, Integer pageSize) {
        // 直接使用传入的userId，无需解析token
        Page<AnswerRecord> pageParam = new Page<>(page, pageSize);
        QueryWrapper<AnswerRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        if (quizId != null) {
            queryWrapper.eq("quiz_id", quizId);
        }
        return answerRecordMapper.selectPage(pageParam, queryWrapper);
    }
}