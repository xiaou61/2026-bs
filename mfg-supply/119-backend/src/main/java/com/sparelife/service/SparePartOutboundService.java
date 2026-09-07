package com.sparelife.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sparelife.entity.SparePartOutbound;
import com.sparelife.mapper.SparePartOutboundMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SparePartOutboundService extends BaseCrudService<SparePartOutbound> {
    private final SparePartOutboundMapper mapper;

    @Override
    protected BaseMapper<SparePartOutbound> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"outbound_no", "part_name", "equipment_name", "receiver_name"};
    }
}
