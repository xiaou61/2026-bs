package com.equipmentshare.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.equipmentshare.entity.ReservationRequest;
import com.equipmentshare.mapper.ReservationRequestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationRequestService extends BaseCrudService<ReservationRequest> {
    private final ReservationRequestMapper purchaseRequestMapper;

    @Override
    protected BaseMapper<ReservationRequest> mapper() {
        return purchaseRequestMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"request_no", "consumable_name", "request_qty", "applicant_name"};
    }
}








