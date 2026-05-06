package com.noisemonitor.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.noisemonitor.entity.PublicFeedback;
import com.noisemonitor.mapper.PublicFeedbackMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PublicFeedbackService extends BaseCrudService<PublicFeedback> {
    private final PublicFeedbackMapper inventoryCheckMapper;

    @Override
    protected BaseMapper<PublicFeedback> mapper() {
        return inventoryCheckMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"check_no", "lab_name", "consumable_name", "book_qty"};
    }
}






