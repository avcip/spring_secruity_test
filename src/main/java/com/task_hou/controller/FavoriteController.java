package com.task_hou.controller;

import com.task_hou.common.CommonResult;
import com.task_hou.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class FavoriteController {
    @Autowired
    FavoriteService favoriteService;

    @PostMapping("/favorites")
    public CommonResult<String> addFavorite(
            HttpServletRequest request,
            @RequestBody Map<String, Integer> favoriteInfo
    ) {
        // 从拦截器注入的请求属性中获取用户ID
        Object userIdObj = request.getAttribute("userId");
        if (userIdObj == null) {
            return CommonResult.error(401, "未登录或令牌无效");
        }
        Integer userId = Integer.parseInt(userIdObj.toString());

        // 从请求体中获取收藏关联的资源ID（示例：知识/案例/帖子ID）
        Integer knowledgeId = favoriteInfo.get("knowledgeId");
        Integer caseId = favoriteInfo.get("caseId");
        Integer forumId = favoriteInfo.get("forumId");

        // 调用Service完成收藏（根据实际业务补充具体逻辑）
        favoriteService.addFavorite(userId, knowledgeId, caseId, forumId);
        return CommonResult.success("收藏成功");
    }
}
