package com.droneinspect.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.droneinspect.entity.DroneDevice;
import com.droneinspect.mapper.DroneDeviceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DroneDeviceService extends BaseCrudService<DroneDevice> {
    private final DroneDeviceMapper mapper;

    @Override
    protected BaseMapper<DroneDevice> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"drone_no", "drone_name", "model_name", "owner_team"};
    }
}
