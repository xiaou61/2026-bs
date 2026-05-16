package com.equipmentshare.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.equipmentshare.entity.UsageRegistration;
import com.equipmentshare.mapper.UsageRegistrationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsageRegistrationService extends BaseCrudService<UsageRegistration> {
    private final UsageRegistrationMapper usageRegistrationMapper;

    @Override
    protected BaseMapper<UsageRegistration> mapper() {
        return usageRegistrationMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"registration_no", "equipment_name", "usage_scene", "teacher_name"};
    }
}








