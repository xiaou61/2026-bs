package com.psychologycare.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.psychologycare.entity.AppointmentRequest;
import com.psychologycare.mapper.AppointmentRequestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppointmentRequestService extends BaseCrudService<AppointmentRequest> {
    private final AppointmentRequestMapper purchaseRequestMapper;

    @Override
    protected BaseMapper<AppointmentRequest> mapper() {
        return purchaseRequestMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"request_no", "consumable_name", "request_qty", "applicant_name"};
    }
}







