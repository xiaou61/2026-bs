package com.floodcity.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.floodcity.entity.DispatchTask;
import com.floodcity.mapper.DispatchTaskMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DispatchTaskService extends BaseCrudService<DispatchTask> {
    private final DispatchTaskMapper mapper;

    @Override
    protected BaseMapper<DispatchTask> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"task_no", "warning_no", "task_type", "responsible_unit"};
    }
}
