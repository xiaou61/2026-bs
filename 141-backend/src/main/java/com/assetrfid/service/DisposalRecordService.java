package com.assetrfid.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.assetrfid.entity.DisposalRecord;
import com.assetrfid.mapper.DisposalRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DisposalRecordService extends BaseCrudService<DisposalRecord> {
    private final DisposalRecordMapper disposalRecordMapper;

    @Override
    protected BaseMapper<DisposalRecord> mapper() {
        return disposalRecordMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"disposal_no", "asset_name", "disposal_type", "handler_name"};
    }
}
