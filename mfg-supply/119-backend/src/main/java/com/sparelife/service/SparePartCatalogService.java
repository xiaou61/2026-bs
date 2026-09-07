package com.sparelife.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sparelife.entity.SparePartCatalog;
import com.sparelife.mapper.SparePartCatalogMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SparePartCatalogService extends BaseCrudService<SparePartCatalog> {
    private final SparePartCatalogMapper mapper;

    @Override
    protected BaseMapper<SparePartCatalog> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"part_no", "part_name", "category_name", "fit_equipment"};
    }
}
