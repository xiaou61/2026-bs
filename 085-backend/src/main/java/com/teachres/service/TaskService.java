package com.teachres.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.teachres.common.BusinessException;
import com.teachres.entity.EvalTask;
import com.teachres.mapper.EvalTaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    private EvalTaskMapper taskMapper;

    public PageInfo<EvalTask> list(String taskName, Long courseId, Integer status, String term,
                                   Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<EvalTask> list = taskMapper.selectList(taskName, courseId, status, term);
        return new PageInfo<>(list);
    }

    public List<EvalTask> activeList() {
        return taskMapper.selectActiveList();
    }

    public void add(EvalTask task, Long userId) {
        if (!StringUtils.hasText(task.getTaskName()) || task.getCourseId() == null) {
            throw new BusinessException("任务名称和课程不能为空");
        }
        if (task.getStartTime() == null || task.getEndTime() == null) {
            throw new BusinessException("任务开始时间和结束时间不能为空");
        }
        if (task.getEndTime().isBefore(task.getStartTime())) {
            throw new BusinessException("结束时间不能早于开始时间");
        }
        if (task.getStatus() == null) {
            task.setStatus(1);
        }
        task.setCreatorId(userId);
        taskMapper.insert(task);
    }

    public void update(EvalTask task) {
        if (task.getId() == null) {
            throw new BusinessException("参数错误");
        }
        if (task.getStartTime() != null && task.getEndTime() != null && task.getEndTime().isBefore(task.getStartTime())) {
            throw new BusinessException("结束时间不能早于开始时间");
        }
        taskMapper.update(task);
    }

    public void updateStatus(Long id, Integer status) {
        EvalTask update = new EvalTask();
        update.setId(id);
        update.setStatus(status);
        updateMapper(update);
    }

    public void delete(Long id) {
        taskMapper.deleteById(id);
    }

    public EvalTask getById(Long id) {
        return taskMapper.selectById(id);
    }

    private void updateMapper(EvalTask task) {
        if (task.getUpdateTime() == null) {
            task.setUpdateTime(LocalDateTime.now());
        }
        taskMapper.update(task);
    }
}
