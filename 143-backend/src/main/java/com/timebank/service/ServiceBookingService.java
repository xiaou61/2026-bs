package com.timebank.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.timebank.entity.ServiceBooking;
import com.timebank.mapper.ServiceBookingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceBookingService extends BaseCrudService<ServiceBooking> {
    private final ServiceBookingMapper serviceBookingMapper;

    @Override
    protected BaseMapper<ServiceBooking> mapper() {
        return serviceBookingMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"booking_no", "project_name", "reserver_name", "booking_time"};
    }
}

