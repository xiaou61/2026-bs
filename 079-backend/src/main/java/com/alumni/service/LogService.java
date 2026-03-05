package com.alumni.service;

import com.alumni.entity.OperationLog;
import com.alumni.mapper.OperationLogMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Service
public class LogService {

    @Autowired
    private OperationLogMapper operationLogMapper;

    public Page<OperationLog> list(Integer pageNum, Integer pageSize, String username, String startTime, String endTime) {
        Page<OperationLog> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<OperationLog> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(username)) {
            wrapper.like(OperationLog::getUsername, username);
        }
        if (StringUtils.hasText(startTime)) {
            wrapper.ge(OperationLog::getCreateTime, LocalDateTime.parse(startTime.replace(" ", "T")));
        }
        if (StringUtils.hasText(endTime)) {
            wrapper.le(OperationLog::getCreateTime, LocalDateTime.parse(endTime.replace(" ", "T")));
        }
        wrapper.orderByDesc(OperationLog::getCreateTime);
        return operationLogMapper.selectPage(page, wrapper);
    }

    public void add(OperationLog log) {
        operationLogMapper.insert(log);
    }

    public void clear() {
        operationLogMapper.delete(null);
    }
}
