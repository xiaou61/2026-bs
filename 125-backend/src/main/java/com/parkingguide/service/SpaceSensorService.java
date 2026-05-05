package com.parkingguide.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.parkingguide.entity.SpaceSensor;
import com.parkingguide.mapper.SpaceSensorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpaceSensorService extends BaseCrudService<SpaceSensor> {
    private final SpaceSensorMapper mapper;

    @Override
    protected BaseMapper<SpaceSensor> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"sensor_no", "space_no", "device_type", "last_heartbeat"};
    }
}
