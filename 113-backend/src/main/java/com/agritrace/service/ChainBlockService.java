package com.agritrace.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.agritrace.entity.ChainBlock;
import com.agritrace.mapper.ChainBlockMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChainBlockService extends BaseCrudService<ChainBlock> {
    private final ChainBlockMapper mapper;

    @Override
    protected BaseMapper<ChainBlock> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"block_no", "batch_no", "tx_hash", "data_hash"};
    }

}
