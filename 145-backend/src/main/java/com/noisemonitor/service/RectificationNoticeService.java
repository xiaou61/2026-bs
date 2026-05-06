package com.noisemonitor.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.noisemonitor.entity.RectificationNotice;
import com.noisemonitor.mapper.RectificationNoticeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RectificationNoticeService extends BaseCrudService<RectificationNotice> {
    private final RectificationNoticeMapper purchaseOrderMapper;

    @Override
    protected BaseMapper<RectificationNotice> mapper() {
        return purchaseOrderMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"order_no", "supplier_name", "consumable_name", "order_amount"};
    }
}






