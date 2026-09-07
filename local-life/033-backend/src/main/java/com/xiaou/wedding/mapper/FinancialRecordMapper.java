package com.xiaou.wedding.mapper;

import com.xiaou.wedding.entity.FinancialRecord;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.math.BigDecimal;

public interface FinancialRecordMapper {
    List<FinancialRecord> list(@Param("recordType") String recordType, @Param("date") String date);

    int insert(FinancialRecord record);

    BigDecimal sumAmount(@Param("recordType") String recordType);
}
