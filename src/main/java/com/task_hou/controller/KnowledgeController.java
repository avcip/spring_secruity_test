package com.task_hou.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.task_hou.common.CommonResult;
import com.task_hou.entity.Knowledge; // 需补充 Knowledge 实体类（假设已存在）
import com.task_hou.service.CaptchaService;
import com.task_hou.service.KnowledgeService; // 需补充 KnowledgeService 接口及实现
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class KnowledgeController {
    @Autowired
    private KnowledgeService knowledgeService;

    // 创建知识内容（管理员/专家权限）
    @PostMapping("/knowledge/create")
    public CommonResult<Knowledge> createKnowledge(@RequestBody Knowledge knowledge) {
        try {
            Knowledge createdKnowledge = knowledgeService.createKnowledge(knowledge);
            return CommonResult.success(createdKnowledge);
        } catch (IllegalArgumentException e) {
            return CommonResult.error(403, e.getMessage()); // 无权限或参数错误
        }
    }

    // 获取知识内容分页列表（支持主题筛选）
    @GetMapping("/knowledge")
    public CommonResult<Map<String, Object>> getKnowledgeList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String topic
    ) {
        Page<Knowledge> knowledgePage = knowledgeService.getKnowledgeList(page, pageSize, topic);
        Map<String, Object> data = new HashMap<>();
        data.put("total", knowledgePage.getTotal());  // 总记录数
        data.put("list", knowledgePage.getRecords()); // 当前页数据列表
        return CommonResult.success(data);
    }
    @GetMapping("/knowledge/{knowledge_id}")
    public CommonResult<Map<String, Object>> getSingleKnowledge(@PathVariable("knowledge_id") Integer knowledgeId) {
        Knowledge knowledge = knowledgeService.getKnowledgeById(knowledgeId);
        if (knowledge == null) {
            return CommonResult.error(404, "知识内容不存在");
        }

        // 构造响应数据（按需求字段映射）
        Map<String, Object> data = new HashMap<>();
        data.put("knowledge_id", knowledge.getKnowledgeId());
        data.put("topic", knowledge.getTopic());
        data.put("content", knowledge.getContent());
        data.put("view_count", knowledge.getViewCount());
        data.put("image_urls", knowledge.getImageUrls());
        data.put("create_time", knowledge.getCreateTime());
        data.put("update_time", knowledge.getUpdateTime());

        return CommonResult.success(data);
    }
}