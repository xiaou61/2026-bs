package com.xiaou.wedding.service.impl;

import com.xiaou.wedding.entity.FinancialRecord;
import com.xiaou.wedding.mapper.FinancialRecordMapper;
import com.xiaou.wedding.service.FinancialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class FinancialServiceImpl implements FinancialService {

    @Autowired
    private FinancialRecordMapper financialRecordMapper;

    @Override
    public List<FinancialRecord> list(String recordType, String date) {
        return financialRecordMapper.list(recordType, date);
    }

    @Override
    public void create(FinancialRecord record) {
        financialRecordMapper.insert(record);
    }

    @Override
    public BigDecimal incomeTotal() {
        return financialRecordMapper.sumAmount("INCOME");
    }

    @Override
    public BigDecimal expenseTotal() {
        return financialRecordMapper.sumAmount("EXPENSE");
    }
}
