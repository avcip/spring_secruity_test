package com.task_hou.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.task_hou.entity.Knowledge;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface KnowledgeMapper extends BaseMapper<Knowledge> {

    /**
     * 获取用户收藏的知识内容
     * @param userId 用户ID
     * @return 收藏知识列表
     */
    List<Map<String, Object>> getUserKnowledgeFavorites(@Param("userId") Long userId);
}