package com.labconsumable.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.labconsumable.entity.StockItem;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StockItemMapper extends BaseMapper<StockItem> {
}
