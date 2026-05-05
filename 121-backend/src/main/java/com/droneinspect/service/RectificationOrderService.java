package com.droneinspect.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.droneinspect.entity.RectificationOrder;
import com.droneinspect.mapper.RectificationOrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RectificationOrderService extends BaseCrudService<RectificationOrder> {
    private final RectificationOrderMapper mapper;

    @Override
    protected BaseMapper<RectificationOrder> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"order_no", "defect_no", "responsible_team", "deadline_time"};
    }
}
