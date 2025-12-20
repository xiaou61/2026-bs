package com.xiaou.snack.wms.controller;

import com.xiaou.snack.wms.entity.inventory.StockCheck;
import com.xiaou.snack.wms.entity.inventory.StockCheckItem;
import lombok.Data;

import java.util.List;

@Data
public class StockCheckRequest {
    private StockCheck check;
    private List<StockCheckItem> items;
}
