package com.parkingguide.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.parkingguide.entity.ParkingArea;
import com.parkingguide.mapper.ParkingAreaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParkingAreaService extends BaseCrudService<ParkingArea> {
    private final ParkingAreaMapper mapper;

    @Override
    protected BaseMapper<ParkingArea> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"area_no", "lot_name", "area_name", "floor_name"};
    }
}
