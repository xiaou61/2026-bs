package com.aquaculture.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.aquaculture.entity.DiseaseWarning;
import com.aquaculture.mapper.DiseaseWarningMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiseaseWarningService extends BaseCrudService<DiseaseWarning> {
    private final DiseaseWarningMapper diseaseWarningMapper;

    @Override
    protected BaseMapper<DiseaseWarning> mapper() {
        return diseaseWarningMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"warning_no", "pond_no", "warning_level", "symptom_text"};
    }
}
