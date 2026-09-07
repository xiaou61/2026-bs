package com.conferencereview.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.conferencereview.entity.CheckinRecord;
import com.conferencereview.mapper.CheckinRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckinRecordService extends BaseCrudService<CheckinRecord> {
    private final CheckinRecordMapper stockWarningMapper;

    @Override
    protected BaseMapper<CheckinRecord> mapper() {
        return stockWarningMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"warning_no", "consumable_name", "current_qty", "warning_level"};
    }
}

