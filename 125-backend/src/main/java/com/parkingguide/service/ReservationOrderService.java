package com.parkingguide.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.parkingguide.entity.ReservationOrder;
import com.parkingguide.mapper.ReservationOrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationOrderService extends BaseCrudService<ReservationOrder> {
    private final ReservationOrderMapper mapper;

    @Override
    protected BaseMapper<ReservationOrder> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"reservation_no", "owner_name", "lot_name", "space_no"};
    }
}
