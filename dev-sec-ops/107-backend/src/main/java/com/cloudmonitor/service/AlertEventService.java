package com.cloudmonitor.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloudmonitor.entity.AlertEvent;
import com.cloudmonitor.mapper.AlertEventMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlertEventService extends BaseCrudService<AlertEvent> {
    private final AlertEventMapper mapper;

    @Override
    protected BaseMapper<AlertEvent> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"event_no", "metric_code", "current_value", "severity"};
    }


}
