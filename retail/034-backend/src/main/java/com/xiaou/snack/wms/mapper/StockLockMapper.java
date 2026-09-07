package com.xiaou.snack.wms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaou.snack.wms.entity.inventory.StockLock;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StockLockMapper extends BaseMapper<StockLock> {
}
