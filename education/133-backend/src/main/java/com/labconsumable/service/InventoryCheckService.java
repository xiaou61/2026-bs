package com.labconsumable.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.labconsumable.entity.InventoryCheck;
import com.labconsumable.mapper.InventoryCheckMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryCheckService extends BaseCrudService<InventoryCheck> {
    private final InventoryCheckMapper inventoryCheckMapper;

    @Override
    protected BaseMapper<InventoryCheck> mapper() {
        return inventoryCheckMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"check_no", "lab_name", "consumable_name", "book_qty"};
    }
}
