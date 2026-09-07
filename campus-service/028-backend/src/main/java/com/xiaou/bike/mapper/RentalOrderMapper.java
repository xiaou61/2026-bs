package com.xiaou.bike.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaou.bike.entity.RentalOrder;
import org.apache.ibatis.annotations.Mapper;

/**
 * 租借订单Mapper接口
 */
@Mapper
public interface RentalOrderMapper extends BaseMapper<RentalOrder> {
}
