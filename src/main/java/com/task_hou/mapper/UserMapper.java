package com.task_hou.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.task_hou.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    // 已有基础CRUD方法（由BaseMapper提供）
    
    // 新增：获取用户收藏内容（对应UserServiceImpl中的调用）
    List<Map<String, Object>> getUserFavorites(
        @Param("userId") Long userId, 
        @Param("type") String type
    );
}