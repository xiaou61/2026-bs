package com.disaster.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.disaster.entity.RescueTask;
import com.disaster.mapper.RescueTaskMapper;
import cn.hutool.core.util.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class TaskService {

    @Autowired
    private RescueTaskMapper taskMapper;

    public void create(RescueTask task, Long publisherId) {
        task.setTaskNo("TK" + IdUtil.getSnowflakeNextIdStr());
        task.setPublisherId(publisherId);
        task.setStatus(0);
        taskMapper.insert(task);
    }

    public Page<RescueTask> page(int pageNum, int pageSize, Integer status, Integer priority, Long disasterId) {
        LambdaQueryWrapper<RescueTask> wrapper = new LambdaQueryWrapper<>();
        if (status != null) {
            wrapper.eq(RescueTask::getStatus, status);
        }
        if (priority != null) {
            wrapper.eq(RescueTask::getPriority, priority);
        }
        if (disasterId != null) {
            wrapper.eq(RescueTask::getDisasterId, disasterId);
        }
        wrapper.orderByDesc(RescueTask::getCreateTime);
        return taskMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
    }

    public Page<RescueTask> myTasks(int pageNum, int pageSize, Long userId, Integer status) {
        LambdaQueryWrapper<RescueTask> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RescueTask::getAssigneeId, userId);
        if (status != null) {
            wrapper.eq(RescueTask::getStatus, status);
        }
        wrapper.orderByDesc(RescueTask::getCreateTime);
        return taskMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
    }

    public RescueTask getById(Long id) {
        return taskMapper.selectById(id);
    }

    public void assign(Long id, Long assigneeId) {
        RescueTask task = new RescueTask();
        task.setId(id);
        task.setAssigneeId(assigneeId);
        task.setStatus(1);
        task.setStartTime(LocalDateTime.now());
        taskMapper.updateById(task);
    }

    public void feedback(Long id, String feedback, String images) {
        RescueTask task = new RescueTask();
        task.setId(id);
        task.setFeedback(feedback);
        task.setFeedbackImages(images);
        task.setFeedbackTime(LocalDateTime.now());
        taskMapper.updateById(task);
    }

    public void complete(Long id) {
        RescueTask task = new RescueTask();
        task.setId(id);
        task.setStatus(2);
        task.setEndTime(LocalDateTime.now());
        taskMapper.updateById(task);
    }

    public void cancel(Long id) {
        RescueTask task = new RescueTask();
        task.setId(id);
        task.setStatus(3);
        taskMapper.updateById(task);
    }

    public void delete(Long id) {
        taskMapper.deleteById(id);
    }

    public Map<String, Object> stats() {
        Map<String, Object> result = new HashMap<>();
        result.put("total", taskMapper.selectCount(null));
        result.put("pending", taskMapper.selectCount(new LambdaQueryWrapper<RescueTask>().eq(RescueTask::getStatus, 0)));
        result.put("processing", taskMapper.selectCount(new LambdaQueryWrapper<RescueTask>().eq(RescueTask::getStatus, 1)));
        result.put("completed", taskMapper.selectCount(new LambdaQueryWrapper<RescueTask>().eq(RescueTask::getStatus, 2)));
        return result;
    }
}
