package com.task_hou.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.task_hou.entity.Admin;
import org.apache.ibatis.annotations.Mapper;

@Mapper  // 关键注解，确保被MyBatis扫描
public interface AdminMapper extends BaseMapper<Admin> {
    // 若需自定义 SQL 方法，可在此添加（无特殊需求时保持空接口）
}