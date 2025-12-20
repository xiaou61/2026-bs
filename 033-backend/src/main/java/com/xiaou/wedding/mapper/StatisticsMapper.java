package com.xiaou.wedding.mapper;

import org.apache.ibatis.annotations.Param;
import java.math.BigDecimal;

public interface StatisticsMapper {
    Integer countCustomer();
    Integer countOrder();
    BigDecimal sumOrderAmount();
    Integer countAppointmentByDate(@Param("date") String date);
}
