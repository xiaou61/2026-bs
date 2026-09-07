package com.oa.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oa.entity.User;
import com.oa.entity.WorkLog;
import com.oa.mapper.UserMapper;
import com.oa.mapper.WorkLogMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class WorkLogService {
    private final WorkLogMapper workLogMapper;
    private final UserMapper userMapper;

    public Page<WorkLog> getList(Integer pageNum, Integer pageSize, String date, String keyword) {
        Page<WorkLog> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<WorkLog> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(date)) {
            wrapper.eq(WorkLog::getLogDate, LocalDate.parse(date));
        }
        if (StringUtils.hasText(keyword)) {
            wrapper.inSql(WorkLog::getUserId, "SELECT id FROM user WHERE real_name LIKE '%" + keyword + "%'");
        }
        wrapper.orderByDesc(WorkLog::getLogDate);
        Page<WorkLog> result = workLogMapper.selectPage(page, wrapper);
        result.getRecords().forEach(w -> {
            User user = userMapper.selectById(w.getUserId());
            if (user != null) w.setRealName(user.getRealName());
        });
        return result;
    }

    public Page<WorkLog> getMyList(Long userId, Integer pageNum, Integer pageSize, String date) {
        Page<WorkLog> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<WorkLog> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WorkLog::getUserId, userId);
        if (StringUtils.hasText(date)) {
            wrapper.eq(WorkLog::getLogDate, LocalDate.parse(date));
        }
        wrapper.orderByDesc(WorkLog::getLogDate);
        return workLogMapper.selectPage(page, wrapper);
    }

    public void add(Long userId, WorkLog workLog) {
        workLog.setUserId(userId);
        workLogMapper.insert(workLog);
    }

    public void update(WorkLog workLog) {
        workLogMapper.updateById(workLog);
    }

    public void delete(Long id) {
        workLogMapper.deleteById(id);
    }
}
