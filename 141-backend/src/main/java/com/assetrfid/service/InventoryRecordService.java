package com.assetrfid.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.assetrfid.entity.InventoryRecord;
import com.assetrfid.mapper.InventoryRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryRecordService extends BaseCrudService<InventoryRecord> {
    private final InventoryRecordMapper inventoryRecordMapper;

    @Override
    protected BaseMapper<InventoryRecord> mapper() {
        return inventoryRecordMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"record_no", "task_no", "checker_name", "difference_note"};
    }
}
