package com.bike.mapper;

import com.bike.entity.RideOrder;
import org.apache.ibatis.annotations.Param;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface RideOrderMapper {
    List<RideOrder> findList(@Param("orderNo") String orderNo, @Param("userId") Long userId, @Param("status") Integer status);
    List<RideOrder> findByUserId(@Param("userId") Long userId);
    RideOrder findById(@Param("id") Long id);
    RideOrder findCurrentByUserId(@Param("userId") Long userId);
    int insert(RideOrder order);
    int update(RideOrder order);
    int countToday();
    BigDecimal sumTodayIncome();
    List<Map<String, Object>> rideTrend(@Param("days") Integer days);
    List<Map<String, Object>> incomeTrend(@Param("days") Integer days);
}
