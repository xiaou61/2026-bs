package com.assetrfid.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.assetrfid.entity.ReturnRecord;
import com.assetrfid.mapper.ReturnRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReturnRecordService extends BaseCrudService<ReturnRecord> {
    private final ReturnRecordMapper returnRecordMapper;

    @Override
    protected BaseMapper<ReturnRecord> mapper() {
        return returnRecordMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"return_no", "application_no", "asset_name", "operator_name"};
    }
}
