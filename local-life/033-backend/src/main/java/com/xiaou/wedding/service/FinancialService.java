package com.xiaou.wedding.service;

import com.xiaou.wedding.entity.FinancialRecord;
import java.math.BigDecimal;
import java.util.List;

public interface FinancialService {
    List<FinancialRecord> list(String recordType, String date);

    void create(FinancialRecord record);

    BigDecimal incomeTotal();

    BigDecimal expenseTotal();
}
