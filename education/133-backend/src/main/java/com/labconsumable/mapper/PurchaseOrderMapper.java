package com.labconsumable.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.labconsumable.entity.PurchaseOrder;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PurchaseOrderMapper extends BaseMapper<PurchaseOrder> {
}
