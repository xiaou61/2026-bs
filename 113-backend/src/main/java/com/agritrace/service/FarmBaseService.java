package com.agritrace.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.agritrace.entity.FarmBase;
import com.agritrace.mapper.FarmBaseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FarmBaseService extends BaseCrudService<FarmBase> {
    private final FarmBaseMapper mapper;

    @Override
    protected BaseMapper<FarmBase> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"base_name", "base_code", "region_name", "owner_name"};
    }

}
