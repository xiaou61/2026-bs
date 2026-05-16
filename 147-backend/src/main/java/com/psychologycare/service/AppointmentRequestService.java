package com.psychologycare.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.psychologycare.entity.AppointmentRequest;
import com.psychologycare.mapper.AppointmentRequestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppointmentRequestService extends BaseCrudService<AppointmentRequest> {
    private final AppointmentRequestMapper appointmentRequestMapper;

    @Override
    protected BaseMapper<AppointmentRequest> mapper() {
        return appointmentRequestMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"appointment_no", "case_theme", "applicant_name", "appointment_status"};
    }
}







