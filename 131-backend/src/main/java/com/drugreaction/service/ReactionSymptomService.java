package com.drugreaction.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.drugreaction.entity.ReactionSymptom;
import com.drugreaction.mapper.ReactionSymptomMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReactionSymptomService extends BaseCrudService<ReactionSymptom> {
    private final ReactionSymptomMapper reactionSymptomMapper;

    @Override
    protected BaseMapper<ReactionSymptom> mapper() {
        return reactionSymptomMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"symptom_no", "report_no", "symptom_name", "onset_time"};
    }
}
