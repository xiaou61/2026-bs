package com.innovationhub.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.innovationhub.entity.RoadshowEvent;
import com.innovationhub.mapper.RoadshowEventMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoadshowEventService extends BaseCrudService<RoadshowEvent> {
    private final RoadshowEventMapper purchaseOrderMapper;

    @Override
    protected BaseMapper<RoadshowEvent> mapper() {
        return purchaseOrderMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"order_no", "supplier_name", "consumable_name", "order_amount"};
    }
}


