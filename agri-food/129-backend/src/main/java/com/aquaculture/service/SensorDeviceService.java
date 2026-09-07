package com.aquaculture.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.aquaculture.entity.SensorDevice;
import com.aquaculture.mapper.SensorDeviceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SensorDeviceService extends BaseCrudService<SensorDevice> {
    private final SensorDeviceMapper sensorDeviceMapper;

    @Override
    protected BaseMapper<SensorDevice> mapper() {
        return sensorDeviceMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"sensor_no", "pond_no", "sensor_type", "install_position"};
    }
}
