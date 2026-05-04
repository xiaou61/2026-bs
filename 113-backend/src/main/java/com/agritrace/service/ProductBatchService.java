package com.agritrace.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.agritrace.entity.ProductBatch;
import com.agritrace.mapper.ProductBatchMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductBatchService extends BaseCrudService<ProductBatch> {
    private final ProductBatchMapper mapper;

    @Override
    protected BaseMapper<ProductBatch> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"batch_no", "product_name", "base_name", "trace_code"};
    }

}
