package com.droneinspect.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.droneinspect.entity.InspectionTask;
import com.droneinspect.mapper.InspectionTaskMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InspectionTaskService extends BaseCrudService<InspectionTask> {
    private final InspectionTaskMapper mapper;

    @Override
    protected BaseMapper<InspectionTask> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"task_no", "route_name", "pilot_name", "plan_time"};
    }
}
