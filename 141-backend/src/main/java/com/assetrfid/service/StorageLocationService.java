package com.assetrfid.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.assetrfid.entity.StorageLocation;
import com.assetrfid.mapper.StorageLocationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StorageLocationService extends BaseCrudService<StorageLocation> {
    private final StorageLocationMapper stockItemMapper;

    @Override
    protected BaseMapper<StorageLocation> mapper() {
        return stockItemMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"stock_no", "consumable_name", "lab_name", "current_qty"};
    }
}




