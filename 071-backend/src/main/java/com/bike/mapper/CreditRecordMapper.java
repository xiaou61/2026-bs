package com.bike.mapper;

import com.bike.entity.CreditRecord;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface CreditRecordMapper {
    List<CreditRecord> findByUserId(@Param("userId") Long userId);
    int insert(CreditRecord record);
}
