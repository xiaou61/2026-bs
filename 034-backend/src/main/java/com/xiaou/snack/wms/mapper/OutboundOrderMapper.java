package com.xiaou.snack.wms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaou.snack.wms.entity.order.OutboundOrder;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OutboundOrderMapper extends BaseMapper<OutboundOrder> {
}
