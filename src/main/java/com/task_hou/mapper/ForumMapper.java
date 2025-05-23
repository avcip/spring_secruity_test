package com.task_hou.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.task_hou.entity.Forum; // 需确保Forum实体类存在
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ForumMapper extends BaseMapper<Forum> {

    /**
     * 获取用户收藏的论坛内容
     * @param userId 用户ID
     * @return 收藏论坛列表
     */
    List<Map<String, Object>> getUserForumFavorites(@Param("userId") Long userId);

    /**
     * 分页查询帖子列表（支持主题搜索）
     * @param page 分页参数（包含页码和每页数量）
     * @param topic 主题关键词（可选，模糊搜索）
     * @return 分页结果（包含total和list）
     */
    IPage<Forum> selectPageByTopic(Page<Forum> page, @Param("topic") String topic);
}