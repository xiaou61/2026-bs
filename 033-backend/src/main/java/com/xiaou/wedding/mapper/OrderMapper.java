package com.xiaou.wedding.mapper;

import com.xiaou.wedding.entity.Order;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface OrderMapper {
    Order findById(@Param("id") Long id);

    List<Order> list(@Param("customerId") Long customerId, @Param("status") String status);

    int insert(Order order);

    int update(Order order);
}
