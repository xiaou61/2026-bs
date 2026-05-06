package com.learningpath.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.learningpath.entity.LearningReminder;
import com.learningpath.mapper.LearningReminderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LearningReminderService extends BaseCrudService<LearningReminder> {
    private final LearningReminderMapper stockWarningMapper;

    @Override
    protected BaseMapper<LearningReminder> mapper() {
        return stockWarningMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"warning_no", "consumable_name", "current_qty", "warning_level"};
    }
}



