package com.assetrfid.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.assetrfid.entity.BorrowApplication;
import com.assetrfid.mapper.BorrowApplicationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BorrowApplicationService extends BaseCrudService<BorrowApplication> {
    private final BorrowApplicationMapper borrowApplicationMapper;

    @Override
    protected BaseMapper<BorrowApplication> mapper() {
        return borrowApplicationMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"application_no", "borrower_name", "asset_name", "planned_return_date"};
    }
}
