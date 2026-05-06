package com.conferencereview.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.conferencereview.entity.GuestRegistration;
import com.conferencereview.mapper.GuestRegistrationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GuestRegistrationService extends BaseCrudService<GuestRegistration> {
    private final GuestRegistrationMapper outboundRecordMapper;

    @Override
    protected BaseMapper<GuestRegistration> mapper() {
        return outboundRecordMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"outbound_no", "consumable_name", "lab_name", "outbound_qty"};
    }
}

