package com.droneinspect.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.droneinspect.entity.FlightRecord;
import com.droneinspect.mapper.FlightRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FlightRecordService extends BaseCrudService<FlightRecord> {
    private final FlightRecordMapper mapper;

    @Override
    protected BaseMapper<FlightRecord> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"record_no", "task_no", "drone_name", "start_time"};
    }
}
