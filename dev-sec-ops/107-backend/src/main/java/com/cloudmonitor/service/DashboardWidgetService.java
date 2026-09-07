package com.cloudmonitor.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloudmonitor.entity.DashboardWidget;
import com.cloudmonitor.mapper.DashboardWidgetMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardWidgetService extends BaseCrudService<DashboardWidget> {
    private final DashboardWidgetMapper mapper;

    @Override
    protected BaseMapper<DashboardWidget> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"widget_name", "widget_type", "metric_code", "status"};
    }


}
