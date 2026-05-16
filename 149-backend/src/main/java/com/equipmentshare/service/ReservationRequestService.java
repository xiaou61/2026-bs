package com.equipmentshare.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.equipmentshare.entity.ReservationRequest;
import com.equipmentshare.mapper.ReservationRequestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationRequestService extends BaseCrudService<ReservationRequest> {
    private final ReservationRequestMapper reservationRequestMapper;

    @Override
    protected BaseMapper<ReservationRequest> mapper() {
        return reservationRequestMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"reservation_no", "equipment_name", "applicant_name", "reservation_time"};
    }
}








