package com.task_hou.service;

public interface FavoriteService {
    // 新增：声明收藏方法（与Controller调用参数一致）
    void addFavorite(Integer userId, Integer knowledgeId, Integer caseId, Integer forumId);
}
