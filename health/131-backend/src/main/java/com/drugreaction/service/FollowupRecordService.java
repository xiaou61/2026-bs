package com.drugreaction.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.drugreaction.entity.FollowupRecord;
import com.drugreaction.mapper.FollowupRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowupRecordService extends BaseCrudService<FollowupRecord> {
    private final FollowupRecordMapper followupRecordMapper;

    @Override
    protected BaseMapper<FollowupRecord> mapper() {
        return followupRecordMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"record_no", "plan_no", "follow_time", "patient_condition"};
    }
}
