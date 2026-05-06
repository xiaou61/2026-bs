package com.psychologycare.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.psychologycare.entity.AssessmentQuestionnaire;
import com.psychologycare.mapper.AssessmentQuestionnaireMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssessmentQuestionnaireService extends BaseCrudService<AssessmentQuestionnaire> {
    private final AssessmentQuestionnaireMapper purchaseOrderMapper;

    @Override
    protected BaseMapper<AssessmentQuestionnaire> mapper() {
        return purchaseOrderMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"order_no", "supplier_name", "consumable_name", "order_amount"};
    }
}







