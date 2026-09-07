package com.bike.mapper;

import com.bike.entity.WalletRecord;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface WalletRecordMapper {
    List<WalletRecord> findByUserId(@Param("userId") Long userId);
    int insert(WalletRecord record);
}
