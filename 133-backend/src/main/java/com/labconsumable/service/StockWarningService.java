package com.labconsumable.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.labconsumable.entity.StockWarning;
import com.labconsumable.mapper.StockWarningMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockWarningService extends BaseCrudService<StockWarning> {
    private final StockWarningMapper stockWarningMapper;

    @Override
    protected BaseMapper<StockWarning> mapper() {
        return stockWarningMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"warning_no", "consumable_name", "current_qty", "warning_level"};
    }
}
