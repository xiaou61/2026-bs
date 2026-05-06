package com.assetrfid.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.assetrfid.entity.DisposalRecord;
import com.assetrfid.mapper.DisposalRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DisposalRecordService extends BaseCrudService<DisposalRecord> {
    private final DisposalRecordMapper warningRuleMapper;

    @Override
    protected BaseMapper<DisposalRecord> mapper() {
        return warningRuleMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"rule_no", "category_name", "min_stock", "warning_level"};
    }
}




