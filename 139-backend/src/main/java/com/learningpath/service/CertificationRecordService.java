package com.learningpath.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.learningpath.entity.CertificationRecord;
import com.learningpath.mapper.CertificationRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CertificationRecordService extends BaseCrudService<CertificationRecord> {
    private final CertificationRecordMapper warningRuleMapper;

    @Override
    protected BaseMapper<CertificationRecord> mapper() {
        return warningRuleMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"rule_no", "category_name", "min_stock", "warning_level"};
    }
}



