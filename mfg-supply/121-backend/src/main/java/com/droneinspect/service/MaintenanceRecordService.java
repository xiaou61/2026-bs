package com.droneinspect.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.droneinspect.entity.MaintenanceRecord;
import com.droneinspect.mapper.MaintenanceRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MaintenanceRecordService extends BaseCrudService<MaintenanceRecord> {
    private final MaintenanceRecordMapper mapper;

    @Override
    protected BaseMapper<MaintenanceRecord> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"maintenance_no", "drone_name", "maintenance_type", "technician_name"};
    }
}
