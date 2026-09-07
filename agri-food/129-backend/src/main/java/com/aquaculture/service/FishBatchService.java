package com.aquaculture.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.aquaculture.entity.FishBatch;
import com.aquaculture.mapper.FishBatchMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FishBatchService extends BaseCrudService<FishBatch> {
    private final FishBatchMapper fishBatchMapper;

    @Override
    protected BaseMapper<FishBatch> mapper() {
        return fishBatchMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"batch_no", "pond_no", "species_name", "stock_date"};
    }
}
