package com.xiaou.snack.wms.controller;

import com.xiaou.snack.wms.common.Result;
import com.xiaou.snack.wms.service.InboundService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inbound")
public class InboundController {
    private final InboundService inboundService;

    public InboundController(InboundService inboundService) {
        this.inboundService = inboundService;
    }

    @PostMapping
    public Result<String> create(@RequestBody InboundRequest request) {
        inboundService.createInbound(request.getOrder(), request.getItems());
        return Result.success();
    }
}
