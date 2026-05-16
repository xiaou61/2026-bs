package com.learningpath.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.learningpath.entity.LearningReminder;
import com.learningpath.mapper.LearningReminderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LearningReminderService extends BaseCrudService<LearningReminder> {
    private final LearningReminderMapper learningReminderMapper;

    @Override
    protected BaseMapper<LearningReminder> mapper() {
        return learningReminderMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"reminder_no", "learner_name", "reminder_type", "channel_name"};
    }
}



