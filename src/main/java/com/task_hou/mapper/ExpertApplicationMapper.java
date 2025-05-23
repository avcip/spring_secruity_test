package com.task_hou.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.task_hou.entity.ExpertApplication;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ExpertApplicationMapper extends BaseMapper<ExpertApplication> {
    // 若需自定义 SQL 方法，可在此添加（无特殊需求时保持空接口）
}