package com.task_hou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.task_hou.entity.Knowledge;
import com.task_hou.mapper.KnowledgeMapper;
import com.task_hou.service.KnowledgeService;
import org.springframework.stereotype.Service;

@Service
public class KnowledgeServiceImpl extends ServiceImpl<KnowledgeMapper, Knowledge> implements KnowledgeService {

    @Override
    public Knowledge createKnowledge(Knowledge knowledge) {  
        Long adminId = 1L;  // 实际应从安全上下文获取（如JWT解析）
        knowledge.setAdminId(adminId);
        save(knowledge);
        return knowledge;
    }

    // 新增：实现接口中缺失的抽象方法
    @Override
    public Knowledge createKnowledge(Knowledge knowledge, Long adminId) {
        knowledge.setAdminId(adminId);  // 直接使用传入的adminId
        save(knowledge);
        return knowledge;
    }

    @Override
    public Page<Knowledge> getKnowledgeList(Integer page, Integer pageSize, String topic) {
        Page<Knowledge> pageParam = new Page<>(page, pageSize);
        QueryWrapper<Knowledge> queryWrapper = new QueryWrapper<>();
        if (topic != null) {
            queryWrapper.eq("topic", topic);
        }
        return page(pageParam, queryWrapper);
    }

    @Override
    public Knowledge getKnowledgeById(Integer knowledgeId) {
        return getById(knowledgeId); // 直接使用 MyBatis-Plus 提供的 getById 方法
    }
}