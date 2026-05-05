package com.sparelife.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sparelife.entity.SparePartInbound;
import com.sparelife.mapper.SparePartInboundMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SparePartInboundService extends BaseCrudService<SparePartInbound> {
    private final SparePartInboundMapper mapper;

    @Override
    protected BaseMapper<SparePartInbound> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"inbound_no", "part_name", "supplier_name", "quality_result"};
    }
}
