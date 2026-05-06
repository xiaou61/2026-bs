package com.assetrfid.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.assetrfid.entity.DepreciationRecord;
import com.assetrfid.mapper.DepreciationRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DepreciationRecordService extends BaseCrudService<DepreciationRecord> {
    private final DepreciationRecordMapper inventoryCheckMapper;

    @Override
    protected BaseMapper<DepreciationRecord> mapper() {
        return inventoryCheckMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"check_no", "lab_name", "consumable_name", "book_qty"};
    }
}




