package com.sparelife.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sparelife.entity.SparePartStock;
import com.sparelife.mapper.SparePartStockMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SparePartStockService extends BaseCrudService<SparePartStock> {
    private final SparePartStockMapper mapper;

    @Override
    protected BaseMapper<SparePartStock> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"stock_no", "part_name", "warehouse_name", "batch_no"};
    }
}
