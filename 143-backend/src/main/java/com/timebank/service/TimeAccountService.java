package com.timebank.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.timebank.entity.TimeAccount;
import com.timebank.mapper.TimeAccountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TimeAccountService extends BaseCrudService<TimeAccount> {
    private final TimeAccountMapper purchaseOrderMapper;

    @Override
    protected BaseMapper<TimeAccount> mapper() {
        return purchaseOrderMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"order_no", "supplier_name", "consumable_name", "order_amount"};
    }
}





