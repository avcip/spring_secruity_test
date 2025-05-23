package com.task_hou.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.task_hou.entity.UserFeedback;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserFeedbackMapper extends BaseMapper<UserFeedback> {
    // 继承BaseMapper已包含基本CRUD方法，无需额外定义
}