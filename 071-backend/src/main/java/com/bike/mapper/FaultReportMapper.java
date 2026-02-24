package com.bike.mapper;

import com.bike.entity.FaultReport;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface FaultReportMapper {
    List<FaultReport> findList(@Param("status") Integer status, @Param("type") Integer type);
    List<FaultReport> findByUserId(@Param("userId") Long userId);
    FaultReport findById(@Param("id") Long id);
    int insert(FaultReport report);
    int update(FaultReport report);
}
