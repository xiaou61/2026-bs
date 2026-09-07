package com.localvoucher.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.localvoucher.entity.ActivityStatRecord;
import com.localvoucher.mapper.ActivityStatRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivityStatRecordService extends BaseCrudService<ActivityStatRecord> {
    private final ActivityStatRecordMapper mapper;

    @Override
    protected BaseMapper<ActivityStatRecord> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"stat_no", "activity_name", "merchant_name", "status"};
    }
}
