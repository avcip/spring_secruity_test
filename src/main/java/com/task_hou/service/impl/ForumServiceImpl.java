package com.task_hou.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.task_hou.entity.Forum;
import com.task_hou.mapper.ForumMapper;
import com.task_hou.service.ForumService;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class ForumServiceImpl extends ServiceImpl<ForumMapper, Forum> implements ForumService {

    @Override
    public Forum postForum(Forum forum, Integer userId) {
        // 自动填充默认值（用户ID、发帖时间、计数默认0）
        forum.setUserId(userId);
        forum.setPostTime(LocalDateTime.now());
        forum.setLikeCount(0);
        forum.setCommentCount(0);
        forum.setViewCount(0);
        // 调用MyBatis-Plus的save方法保存
        save(forum);
        return forum;
    }

    @Override
    public IPage<Forum> getForumList(String topic, Integer page, Integer pageSize) {
        // 处理分页参数默认值（page=1，pageSize=10）
        Page<Forum> pageParam = new Page<>(page == null ? 1 : page, pageSize == null ? 10 : pageSize);
        // 调用自定义Mapper方法查询
        return baseMapper.selectPageByTopic(pageParam, topic);
    }
}