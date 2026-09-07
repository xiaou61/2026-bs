package com.innovationhub.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.innovationhub.entity.MentorProfile;
import com.innovationhub.mapper.MentorProfileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MentorProfileService extends BaseCrudService<MentorProfile> {
    private final MentorProfileMapper supplierProfileMapper;

    @Override
    protected BaseMapper<MentorProfile> mapper() {
        return supplierProfileMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"supplier_no", "supplier_name", "contact_name", "phone_number"};
    }
}


