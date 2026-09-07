package com.xiaou.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaou.common.Result;
import com.xiaou.entity.Visit;
import com.xiaou.service.VisitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "探访管理")
@RestController
@RequestMapping("/api/visit")
@RequiredArgsConstructor
public class VisitController {

    private final VisitService visitService;

    @Operation(summary = "申请探访")
    @PostMapping("/apply")
    public Result<Void> apply(@AuthenticationPrincipal Long userId,
                              @RequestBody Visit visit) {
        visitService.apply(visit, userId);
        return Result.success();
    }

    @Operation(summary = "我的探访记录")
    @GetMapping("/my")
    public Result<IPage<Visit>> myVisits(@AuthenticationPrincipal Long userId,
                                         @RequestParam(defaultValue = "1") Integer current,
                                         @RequestParam(defaultValue = "10") Integer size) {
        return Result.success(visitService.pageByUser(userId, current, size));
    }

    @Operation(summary = "探访列表(管理)")
    @GetMapping("/list")
    public Result<IPage<Visit>> list(@RequestParam(required = false) Long elderId,
                                     @RequestParam(defaultValue = "1") Integer current,
                                     @RequestParam(defaultValue = "10") Integer size,
                                     @RequestParam(required = false) Integer status) {
        return Result.success(visitService.pageList(elderId, current, size, status));
    }

    @Operation(summary = "审批探访")
    @PostMapping("/approve/{id}")
    public Result<Void> approve(@AuthenticationPrincipal Long userId,
                                @PathVariable Long id,
                                @RequestParam Integer status,
                                @RequestParam(required = false) String remark) {
        visitService.approve(id, status, remark, userId);
        return Result.success();
    }
}
