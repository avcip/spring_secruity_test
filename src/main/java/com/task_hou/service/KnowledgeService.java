package com.task_hou.service;

import com.task_hou.entity.Knowledge;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public interface KnowledgeService {
    /**
     * 创建知识内容（需专家/管理员权限）
     * @param knowledge 知识内容实体
     * @return 创建后的知识对象
     */
    Knowledge createKnowledge(Knowledge knowledge);

    Knowledge createKnowledge(Knowledge knowledge, Long adminId);

    /**
     * 分页查询知识内容
     * @param page 当前页码
     * @param pageSize 每页数量
     * @param topic 主题筛选（可选）
     * @return 分页结果
     */
    Page<Knowledge> getKnowledgeList(Integer page, Integer pageSize, String topic);

    /**
     * 根据ID获取单个知识内容
     * @param knowledgeId 知识ID
     * @return 知识实体
     */
    Knowledge getKnowledgeById(Integer knowledgeId);
}