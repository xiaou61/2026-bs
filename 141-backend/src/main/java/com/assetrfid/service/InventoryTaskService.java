package com.assetrfid.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.assetrfid.entity.InventoryTask;
import com.assetrfid.mapper.InventoryTaskMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryTaskService extends BaseCrudService<InventoryTask> {
    private final InventoryTaskMapper purchaseRequestMapper;

    @Override
    protected BaseMapper<InventoryTask> mapper() {
        return purchaseRequestMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"request_no", "consumable_name", "request_qty", "applicant_name"};
    }
}




