package com.drugreaction.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.drugreaction.entity.DrugCatalog;
import com.drugreaction.mapper.DrugCatalogMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DrugCatalogService extends BaseCrudService<DrugCatalog> {
    private final DrugCatalogMapper drugCatalogMapper;

    @Override
    protected BaseMapper<DrugCatalog> mapper() {
        return drugCatalogMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"drug_no", "drug_name", "dosage_form", "manufacturer_name"};
    }
}
