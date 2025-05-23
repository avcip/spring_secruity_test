package com.task_hou;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.task_hou.mapper") // 添加 Mapper 扫描路径
public class TaskHouApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskHouApplication.class, args);
    }

}
