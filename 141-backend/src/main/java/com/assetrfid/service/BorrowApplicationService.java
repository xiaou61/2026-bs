package com.assetrfid.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.assetrfid.entity.BorrowApplication;
import com.assetrfid.mapper.BorrowApplicationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BorrowApplicationService extends BaseCrudService<BorrowApplication> {
    private final BorrowApplicationMapper purchaseOrderMapper;

    @Override
    protected BaseMapper<BorrowApplication> mapper() {
        return purchaseOrderMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"order_no", "supplier_name", "consumable_name", "order_amount"};
    }
}




