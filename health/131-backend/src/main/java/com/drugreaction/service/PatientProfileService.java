package com.drugreaction.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.drugreaction.entity.PatientProfile;
import com.drugreaction.mapper.PatientProfileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientProfileService extends BaseCrudService<PatientProfile> {
    private final PatientProfileMapper patientProfileMapper;

    @Override
    protected BaseMapper<PatientProfile> mapper() {
        return patientProfileMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"patient_no", "patient_name", "gender_name", "phone_number"};
    }
}
