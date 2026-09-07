package com.crossborder.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.crossborder.entity.CrossBorderOrder;
import com.crossborder.mapper.CrossBorderOrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CrossBorderOrderService extends BaseCrudService<CrossBorderOrder> {
    private final CrossBorderOrderMapper mapper;

    @Override
    protected BaseMapper<CrossBorderOrder> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"order_no", "store_name", "customer_name", "destination_country"};
    }

    @Override
    public void updateStatus(Long id, String status) {
        mapper.update(null, new UpdateWrapper<CrossBorderOrder>().eq("id", id).set("status", status).set("order_status", status));
    }
}
