package com.xiaou.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaou.common.Result;
import com.xiaou.entity.CarePlan;
import com.xiaou.entity.CareRecord;
import com.xiaou.service.CareService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "护理服务")
@RestController
@RequestMapping("/api/care")
@RequiredArgsConstructor
public class CareController {

    private final CareService careService;

    @Operation(summary = "老人护理计划")
    @GetMapping("/plan/{elderId}")
    public Result<List<CarePlan>> plans(@PathVariable Long elderId) {
        return Result.success(careService.getPlansByElder(elderId));
    }

    @Operation(summary = "添加护理计划")
    @PostMapping("/plan")
    public Result<Void> addPlan(@RequestBody CarePlan plan) {
        careService.addPlan(plan);
        return Result.success();
    }

    @Operation(summary = "护理记录列表")
    @GetMapping("/record")
    public Result<IPage<CareRecord>> records(@RequestParam(required = false) Long elderId,
                                             @RequestParam(defaultValue = "1") Integer current,
                                             @RequestParam(defaultValue = "10") Integer size) {
        return Result.success(careService.pageRecords(elderId, current, size));
    }

    @Operation(summary = "添加护理记录")
    @PostMapping("/record")
    public Result<Void> addRecord(@AuthenticationPrincipal Long userId,
                                  @RequestBody CareRecord record) {
        careService.addRecord(record, userId);
        return Result.success();
    }
}
