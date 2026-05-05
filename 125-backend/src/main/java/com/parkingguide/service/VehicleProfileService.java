package com.parkingguide.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.parkingguide.entity.VehicleProfile;
import com.parkingguide.mapper.VehicleProfileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VehicleProfileService extends BaseCrudService<VehicleProfile> {
    private final VehicleProfileMapper mapper;

    @Override
    protected BaseMapper<VehicleProfile> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"vehicle_no", "owner_name", "plate_no", "vehicle_type"};
    }
}
