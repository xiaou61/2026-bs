package com.psychologycare.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.psychologycare.entity.AssessmentQuestionnaire;
import com.psychologycare.mapper.AssessmentQuestionnaireMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssessmentQuestionnaireService extends BaseCrudService<AssessmentQuestionnaire> {
    private final AssessmentQuestionnaireMapper assessmentQuestionnaireMapper;

    @Override
    protected BaseMapper<AssessmentQuestionnaire> mapper() {
        return assessmentQuestionnaireMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"questionnaire_no", "case_theme", "questionnaire_name", "target_group"};
    }
}







