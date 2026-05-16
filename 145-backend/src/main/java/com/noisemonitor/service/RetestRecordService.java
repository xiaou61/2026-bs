package com.noisemonitor.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.noisemonitor.entity.RetestRecord;
import com.noisemonitor.mapper.RetestRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RetestRecordService extends BaseCrudService<RetestRecord> {
    private final RetestRecordMapper retestRecordMapper;

    @Override
    protected BaseMapper<RetestRecord> mapper() {
        return retestRecordMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"retest_no", "complaint_title", "retest_officer", "retest_time"};
    }
}






