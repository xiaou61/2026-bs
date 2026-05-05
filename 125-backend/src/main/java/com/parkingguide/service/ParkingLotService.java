package com.parkingguide.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.parkingguide.entity.ParkingLot;
import com.parkingguide.mapper.ParkingLotMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParkingLotService extends BaseCrudService<ParkingLot> {
    private final ParkingLotMapper mapper;

    @Override
    protected BaseMapper<ParkingLot> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"lot_no", "lot_name", "city_name", "address_name"};
    }
}
