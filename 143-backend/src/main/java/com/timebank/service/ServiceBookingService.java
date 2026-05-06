package com.timebank.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.timebank.entity.ServiceBooking;
import com.timebank.mapper.ServiceBookingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceBookingService extends BaseCrudService<ServiceBooking> {
    private final ServiceBookingMapper purchaseRequestMapper;

    @Override
    protected BaseMapper<ServiceBooking> mapper() {
        return purchaseRequestMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"request_no", "consumable_name", "request_qty", "applicant_name"};
    }
}





