package com.crossborder.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.crossborder.entity.ClearanceDeclaration;
import com.crossborder.mapper.ClearanceDeclarationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClearanceDeclarationService extends BaseCrudService<ClearanceDeclaration> {
    private final ClearanceDeclarationMapper mapper;

    @Override
    protected BaseMapper<ClearanceDeclaration> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"declaration_no", "order_no", "customs_port", "declare_type"};
    }
}
