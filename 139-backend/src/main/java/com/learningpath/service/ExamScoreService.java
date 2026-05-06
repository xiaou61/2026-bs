package com.learningpath.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.learningpath.entity.ExamScore;
import com.learningpath.mapper.ExamScoreMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExamScoreService extends BaseCrudService<ExamScore> {
    private final ExamScoreMapper inboundRecordMapper;

    @Override
    protected BaseMapper<ExamScore> mapper() {
        return inboundRecordMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"inbound_no", "order_no", "consumable_name", "inbound_qty"};
    }
}



