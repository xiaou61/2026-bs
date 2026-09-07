package com.agritrace.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.agritrace.entity.InputMaterial;
import com.agritrace.mapper.InputMaterialMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InputMaterialService extends BaseCrudService<InputMaterial> {
    private final InputMaterialMapper mapper;

    @Override
    protected BaseMapper<InputMaterial> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"material_name", "material_no", "batch_no", "supplier_name"};
    }

}
