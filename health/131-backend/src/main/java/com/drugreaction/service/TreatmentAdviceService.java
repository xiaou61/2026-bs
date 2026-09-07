package com.drugreaction.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.drugreaction.entity.TreatmentAdvice;
import com.drugreaction.mapper.TreatmentAdviceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TreatmentAdviceService extends BaseCrudService<TreatmentAdvice> {
    private final TreatmentAdviceMapper treatmentAdviceMapper;

    @Override
    protected BaseMapper<TreatmentAdvice> mapper() {
        return treatmentAdviceMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"advice_no", "report_no", "advice_type", "advice_content"};
    }
}
