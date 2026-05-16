package com.learningpath.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.learningpath.entity.CertificationRecord;
import com.learningpath.mapper.CertificationRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CertificationRecordService extends BaseCrudService<CertificationRecord> {
    private final CertificationRecordMapper certificationRecordMapper;

    @Override
    protected BaseMapper<CertificationRecord> mapper() {
        return certificationRecordMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"cert_no", "learner_name", "certification_name", "issuer_name"};
    }
}



