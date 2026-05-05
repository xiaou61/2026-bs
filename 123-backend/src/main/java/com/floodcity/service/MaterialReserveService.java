package com.floodcity.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.floodcity.entity.MaterialReserve;
import com.floodcity.mapper.MaterialReserveMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MaterialReserveService extends BaseCrudService<MaterialReserve> {
    private final MaterialReserveMapper mapper;

    @Override
    protected BaseMapper<MaterialReserve> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"material_no", "material_name", "material_type", "warehouse_name"};
    }
}
