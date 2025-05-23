package com.task_hou.controller;

import com.task_hou.common.CommonResult;
import com.task_hou.entity.AnswerRecord;
import com.task_hou.entity.Quiz;
import com.task_hou.service.QuizService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
public class QuizController {
    @Autowired
    private QuizService quizService;

    // 获取测验题目列表（支持难度筛选）
    @GetMapping("/quizzes")
    public CommonResult<Quiz[]> getQuizList(
        @RequestParam(required = false) String difficulty
    ) {
        List<Quiz> quizList = quizService.getQuizList(difficulty);
        return CommonResult.success(quizList.toArray(new Quiz[0]));
    }

    // 提交答题记录（需登录，验证由拦截器完成）
    /**
     * 提交答题记录（优化版）
     * @param record 答题记录实体（包含 quizId、userAnswer）
     * @param request 请求对象（用于获取用户ID）
     * @return 包含答题记录、正确答案和解析的结果
     */
    @PostMapping("/answer/records")
    public CommonResult<Map<String, Object>> submitAnswerRecord(
        @RequestBody AnswerRecord record,
        HttpServletRequest request
    ) {
        // 参数校验：确保题目ID和用户答案存在
        if (record.getQuizId() == null || record.getUserAnswer() == null) {
            return CommonResult.error(400, "题目ID和用户答案不能为空");
        }

        // 从拦截器注入的请求属性中获取用户ID
        Object userIdObj = request.getAttribute("userId");
        if (userIdObj == null) {
            return CommonResult.error(401, "未登录或令牌无效");
        }
        Long userId = Long.parseLong(userIdObj.toString());

        // 调用服务层保存记录并返回结果
        Map<String, Object> result = quizService.submitAnswerRecord(userId, record);
        return CommonResult.success(result);
    }

    // 获取用户答题记录（需登录，验证由拦截器完成）
    @GetMapping("/answer/records")
    public CommonResult<Page<AnswerRecord>> getUserAnswerRecords(
        HttpServletRequest request,
        @RequestParam(required = false) Integer quiz_id,
        @RequestParam(defaultValue = "1") Integer page,
        @RequestParam(defaultValue = "10") Integer page_size
    ) {
        // 从拦截器注入的请求属性中获取用户ID
        Object userIdObj = request.getAttribute("userId");
        if (userIdObj == null) {
            return CommonResult.error(401, "未登录或令牌无效");
        }
        Long userId = Long.parseLong(userIdObj.toString());

        // 调用服务层时传递用户ID而非令牌
        return CommonResult.success(quizService.getUserAnswerRecords(userId, quiz_id, page, page_size));
    }

    // 获取单个测验题目
    @GetMapping("/quizzes/{quiz_id}")
    public CommonResult<Quiz> getQuizById(
        @PathVariable Integer quiz_id
    ) {
        Quiz quiz = quizService.getQuizById(quiz_id);
        return quiz != null ? CommonResult.success(quiz) : CommonResult.error(404, "题目不存在");
    }
}