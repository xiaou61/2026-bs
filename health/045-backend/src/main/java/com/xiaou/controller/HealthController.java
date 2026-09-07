package com.xiaou.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaou.common.Result;
import com.xiaou.entity.HealthRecord;
import com.xiaou.service.HealthRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "健康档案")
@RestController
@RequestMapping("/api/health")
@RequiredArgsConstructor
public class HealthController {

    private final HealthRecordService healthRecordService;

    @Operation(summary = "老人健康记录列表")
    @GetMapping("/{elderId}")
    public Result<IPage<HealthRecord>> list(@PathVariable Long elderId,
                                            @RequestParam(defaultValue = "1") Integer current,
                                            @RequestParam(defaultValue = "10") Integer size) {
        return Result.success(healthRecordService.pageByElder(elderId, current, size));
    }

    @Operation(summary = "添加健康记录")
    @PostMapping
    public Result<Void> add(@AuthenticationPrincipal Long userId,
                            @RequestBody HealthRecord record) {
        healthRecordService.addRecord(record, userId);
        return Result.success();
    }

    @Operation(summary = "健康记录详情")
    @GetMapping("/record/{id}")
    public Result<HealthRecord> detail(@PathVariable Long id) {
        return Result.success(healthRecordService.getById(id));
    }
}
