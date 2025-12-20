package com.xiaou.snack.wms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaou.snack.wms.entity.order.InboundOrder;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface InboundOrderMapper extends BaseMapper<InboundOrder> {
}
