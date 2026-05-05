package com.aquaculture.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.aquaculture.entity.FeedingRecord;
import com.aquaculture.mapper.FeedingRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedingRecordService extends BaseCrudService<FeedingRecord> {
    private final FeedingRecordMapper feedingRecordMapper;

    @Override
    protected BaseMapper<FeedingRecord> mapper() {
        return feedingRecordMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"feed_no", "pond_no", "feed_time", "operator_name"};
    }
}
