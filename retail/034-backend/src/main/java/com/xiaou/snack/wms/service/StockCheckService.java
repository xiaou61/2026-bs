package com.xiaou.snack.wms.service;

import com.xiaou.snack.wms.entity.inventory.StockCheck;
import com.xiaou.snack.wms.entity.inventory.StockCheckItem;

import java.util.List;

public interface StockCheckService {
    void createCheck(StockCheck check, List<StockCheckItem> items);
}
