package com.labconsumable.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.labconsumable.entity.ConsumableCatalog;
import com.labconsumable.mapper.ConsumableCatalogMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConsumableCatalogService extends BaseCrudService<ConsumableCatalog> {
    private final ConsumableCatalogMapper consumableCatalogMapper;

    @Override
    protected BaseMapper<ConsumableCatalog> mapper() {
        return consumableCatalogMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"consumable_no", "consumable_name", "spec_model", "unit_name"};
    }
}
