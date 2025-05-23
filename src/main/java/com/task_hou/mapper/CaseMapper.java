package com.task_hou.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.task_hou.entity.Case;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface CaseMapper extends BaseMapper<Case> {

    // 基础CRUD方法已由BaseMapper提供（insert/selectById/updateById等）

    /**
     * 根据分类查询案例（带分页）
     * @param page 分页参数
     * @param category 案例分类
     * @return 分页后的案例列表
     */
    IPage<Case> selectByCategory(Page<Case> page, @Param("category") String category);

    /**
     * 根据管理员ID查询案例（按创建时间倒序）
     * @param adminId 管理员ID
     * @return 案例列表
     */
    List<Case> selectByAdminId(@Param("adminId") Integer adminId);

    /**
     * 获取浏览量前N的热门案例
     * @param topN 前N条
     * @return 热门案例列表
     */
    List<Case> selectHotCases(@Param("topN") Integer topN);

    /**
     * 获取用户收藏的案例内容
     * @param userId 用户ID
     * @return 收藏案例列表
     */
    List<Map<String, Object>> getUserCaseFavorites(@Param("userId") Long userId);
}