package com.agritrace.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.agritrace.entity.LogisticsRecord;
import com.agritrace.mapper.LogisticsRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogisticsRecordService extends BaseCrudService<LogisticsRecord> {
    private final LogisticsRecordMapper mapper;

    @Override
    protected BaseMapper<LogisticsRecord> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"logistics_no", "batch_no", "from_node", "to_node"};
    }

}
