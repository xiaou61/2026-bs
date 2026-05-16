package com.assetrfid.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.assetrfid.entity.StorageLocation;
import com.assetrfid.mapper.StorageLocationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StorageLocationService extends BaseCrudService<StorageLocation> {
    private final StorageLocationMapper storageLocationMapper;

    @Override
    protected BaseMapper<StorageLocation> mapper() {
        return storageLocationMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"location_no", "asset_name", "location_name", "current_qty"};
    }
}
