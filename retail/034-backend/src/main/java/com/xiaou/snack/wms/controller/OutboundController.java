package com.xiaou.snack.wms.controller;

import com.xiaou.snack.wms.common.Result;
import com.xiaou.snack.wms.service.OutboundService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/outbound")
public class OutboundController {
    private final OutboundService outboundService;

    public OutboundController(OutboundService outboundService) {
        this.outboundService = outboundService;
    }

    @PostMapping
    public Result<String> create(@RequestBody OutboundRequest request) {
        outboundService.createOutbound(request.getOrder(), request.getItems());
        return Result.success();
    }
}
