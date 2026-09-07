package com.parkingguide.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.parkingguide.entity.ParkingSpace;
import com.parkingguide.mapper.ParkingSpaceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParkingSpaceService extends BaseCrudService<ParkingSpace> {
    private final ParkingSpaceMapper mapper;

    @Override
    protected BaseMapper<ParkingSpace> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"space_no", "area_name", "space_type", "plate_label"};
    }
}
