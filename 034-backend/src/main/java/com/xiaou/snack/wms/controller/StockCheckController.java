package com.xiaou.snack.wms.controller;

import com.xiaou.snack.wms.common.Result;
import com.xiaou.snack.wms.service.StockCheckService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stock-check")
public class StockCheckController {
    private final StockCheckService stockCheckService;

    public StockCheckController(StockCheckService stockCheckService) {
        this.stockCheckService = stockCheckService;
    }

    @PostMapping
    public Result<String> create(@RequestBody StockCheckRequest request) {
        stockCheckService.createCheck(request.getCheck(), request.getItems());
        return Result.success();
    }
}
