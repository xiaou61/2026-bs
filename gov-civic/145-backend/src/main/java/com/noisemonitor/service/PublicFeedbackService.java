package com.noisemonitor.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.noisemonitor.entity.PublicFeedback;
import com.noisemonitor.mapper.PublicFeedbackMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PublicFeedbackService extends BaseCrudService<PublicFeedback> {
    private final PublicFeedbackMapper publicFeedbackMapper;

    @Override
    protected BaseMapper<PublicFeedback> mapper() {
        return publicFeedbackMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"feedback_no", "complaint_title", "feedback_topic", "feedback_channel"};
    }
}






