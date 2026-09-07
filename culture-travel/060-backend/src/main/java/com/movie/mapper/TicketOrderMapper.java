package com.movie.mapper;

import com.movie.entity.TicketOrder;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

public interface TicketOrderMapper {
    List<TicketOrder> selectPage(@Param("orderNo") String orderNo, @Param("status") Integer status);
    List<TicketOrder> selectByUserId(Long userId);
    TicketOrder selectById(Long id);
    int insert(TicketOrder order);
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);
    int updatePayTime(@Param("id") Long id);
    int count();
    Double sumTotalPrice();
    List<Map<String, Object>> selectOrderTrend();
    List<Map<String, Object>> selectBoxOfficeRank();
}
