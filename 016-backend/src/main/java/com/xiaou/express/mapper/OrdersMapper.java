package com.xiaou.express.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaou.express.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {
}

