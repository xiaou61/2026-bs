package com.cloudmonitor.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloudmonitor.entity.MaintenanceWindow;
import com.cloudmonitor.mapper.MaintenanceWindowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MaintenanceWindowService extends BaseCrudService<MaintenanceWindow> {
    private final MaintenanceWindowMapper mapper;

    @Override
    protected BaseMapper<MaintenanceWindow> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"window_name", "start_time", "end_time", "owner_name"};
    }


}
