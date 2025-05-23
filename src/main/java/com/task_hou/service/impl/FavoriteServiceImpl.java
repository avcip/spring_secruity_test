package com.task_hou.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.task_hou.entity.Favorite;
import com.task_hou.mapper.FavoriteMapper;
import com.task_hou.service.FavoriteService;
import org.springframework.stereotype.Service;

@Service
public class FavoriteServiceImpl extends ServiceImpl<FavoriteMapper, Favorite> implements FavoriteService {

    @Override
    public void addFavorite(Integer userId, Integer knowledgeId, Integer caseId, Integer forumId) {
        // 创建收藏记录实体（需确保Favorite实体包含这些字段）
        Favorite favorite = new Favorite();
        favorite.setUserId(userId);
        favorite.setKnowledgeId(knowledgeId);
        favorite.setCaseId(caseId);
        favorite.setForumId(forumId);
        
        // 使用MyBatis-Plus的save方法保存到数据库（继承自ServiceImpl）
        save(favorite);
    }
}
