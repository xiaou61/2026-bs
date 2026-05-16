package com.assetrfid.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.assetrfid.entity.InventoryTask;
import com.assetrfid.mapper.InventoryTaskMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryTaskService extends BaseCrudService<InventoryTask> {
    private final InventoryTaskMapper inventoryTaskMapper;

    @Override
    protected BaseMapper<InventoryTask> mapper() {
        return inventoryTaskMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"task_no", "asset_name", "planned_count", "executor_name"};
    }
}
