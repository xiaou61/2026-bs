package com.psychologycare.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.psychologycare.entity.CounselCase;
import com.psychologycare.mapper.CounselCaseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CounselCaseService extends BaseCrudService<CounselCase> {
    private final CounselCaseMapper consumableCatalogMapper;

    @Override
    protected BaseMapper<CounselCase> mapper() {
        return consumableCatalogMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"consumable_no", "consumable_name", "spec_model", "unit_name"};
    }
}







