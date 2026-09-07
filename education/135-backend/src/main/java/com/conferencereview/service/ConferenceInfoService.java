package com.conferencereview.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.conferencereview.entity.ConferenceInfo;
import com.conferencereview.mapper.ConferenceInfoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConferenceInfoService extends BaseCrudService<ConferenceInfo> {
    private final ConferenceInfoMapper consumableCatalogMapper;

    @Override
    protected BaseMapper<ConferenceInfo> mapper() {
        return consumableCatalogMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"consumable_no", "consumable_name", "spec_model", "unit_name"};
    }
}

