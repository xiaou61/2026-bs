package com.learningpath.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.learningpath.entity.AssessmentExam;
import com.learningpath.mapper.AssessmentExamMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssessmentExamService extends BaseCrudService<AssessmentExam> {
    private final AssessmentExamMapper purchaseOrderMapper;

    @Override
    protected BaseMapper<AssessmentExam> mapper() {
        return purchaseOrderMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"order_no", "supplier_name", "consumable_name", "order_amount"};
    }
}



