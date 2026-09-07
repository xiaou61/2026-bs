package com.carbonmanage.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.carbonmanage.entity.AccountingPeriod;
import com.carbonmanage.mapper.AccountingPeriodMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountingPeriodService extends BaseCrudService<AccountingPeriod> {
    private final AccountingPeriodMapper accountingPeriodMapper;

    @Override
    protected BaseMapper<AccountingPeriod> mapper() {
        return accountingPeriodMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"period_no", "company_no", "period_month", "boundary_scope"};
    }
}
