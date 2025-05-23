package com.task_hou.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.task_hou.entity.Quiz;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QuizMapper extends BaseMapper<Quiz> {
    // 可根据需求添加自定义 SQL 方法（如无特殊需求，空接口即可）
}