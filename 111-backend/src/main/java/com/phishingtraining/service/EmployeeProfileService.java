package com.phishingtraining.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.phishingtraining.entity.EmployeeProfile;
import com.phishingtraining.mapper.EmployeeProfileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeProfileService extends BaseCrudService<EmployeeProfile> {
    private final EmployeeProfileMapper mapper;

    @Override
    protected BaseMapper<EmployeeProfile> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"employee_name", "employee_no", "email", "department_name"};
    }

}
