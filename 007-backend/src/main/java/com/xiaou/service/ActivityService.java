package com.xiaou.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.entity.Activity;
import com.xiaou.mapper.ActivityMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ActivityService extends ServiceImpl<ActivityMapper, Activity> {

    public IPage<Activity> getActivityList(Integer page, Integer size, Integer status, String title) {
        Page<Activity> pageParam = new Page<>(page, size);
        QueryWrapper<Activity> wrapper = new QueryWrapper<>();
        
        if (status != null) {
            wrapper.eq("status", status);
        }
        if (title != null && !title.isEmpty()) {
            wrapper.like("title", title);
        }
        
        wrapper.orderByDesc("create_time");
        return this.page(pageParam, wrapper);
    }

    public void incrementParticipants(Long activityId) {
        Activity activity = this.getById(activityId);
        if (activity != null) {
            activity.setCurrentParticipants(activity.getCurrentParticipants() + 1);
            activity.setUpdateTime(LocalDateTime.now());
            this.updateById(activity);
        }
    }

    public void decrementParticipants(Long activityId) {
        Activity activity = this.getById(activityId);
        if (activity != null && activity.getCurrentParticipants() > 0) {
            activity.setCurrentParticipants(activity.getCurrentParticipants() - 1);
            activity.setUpdateTime(LocalDateTime.now());
            this.updateById(activity);
        }
    }
}

