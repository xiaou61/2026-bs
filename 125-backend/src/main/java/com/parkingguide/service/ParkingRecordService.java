package com.parkingguide.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.parkingguide.entity.ParkingRecord;
import com.parkingguide.mapper.ParkingRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParkingRecordService extends BaseCrudService<ParkingRecord> {
    private final ParkingRecordMapper mapper;

    @Override
    protected BaseMapper<ParkingRecord> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"record_no", "reservation_no", "plate_no", "enter_time"};
    }
}
