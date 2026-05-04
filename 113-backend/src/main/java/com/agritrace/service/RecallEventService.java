package com.agritrace.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.agritrace.entity.RecallEvent;
import com.agritrace.mapper.RecallEventMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecallEventService extends BaseCrudService<RecallEvent> {
    private final RecallEventMapper mapper;

    @Override
    protected BaseMapper<RecallEvent> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"recall_no", "batch_no", "product_name", "reason_text"};
    }

}
