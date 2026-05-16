package com.noisemonitor.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.noisemonitor.entity.PatrolRecord;
import com.noisemonitor.mapper.PatrolRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatrolRecordService extends BaseCrudService<PatrolRecord> {
    private final PatrolRecordMapper patrolRecordMapper;

    @Override
    protected BaseMapper<PatrolRecord> mapper() {
        return patrolRecordMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"patrol_no", "complaint_title", "patrol_officer", "patrol_result"};
    }
}






