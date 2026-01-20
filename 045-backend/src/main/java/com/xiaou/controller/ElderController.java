package com.xiaou.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaou.common.Result;
import com.xiaou.entity.Elder;
import com.xiaou.service.ElderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "老人管理")
@RestController
@RequestMapping("/api/elder")
@RequiredArgsConstructor
public class ElderController {

    private final ElderService elderService;

    @Operation(summary = "老人列表")
    @GetMapping("/list")
    public Result<IPage<Elder>> list(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) Integer careLevel) {
        return Result.success(elderService.pageList(current, size, name, status, careLevel));
    }

    @Operation(summary = "老人详情")
    @GetMapping("/{id}")
    public Result<Elder> detail(@PathVariable Long id) {
        return Result.success(elderService.getById(id));
    }

    @Operation(summary = "入住登记")
    @PostMapping("/checkin")
    public Result<Void> checkIn(@RequestBody Elder elder) {
        elderService.checkIn(elder);
        return Result.success();
    }

    @Operation(summary = "更新老人信息")
    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable Long id, @RequestBody Elder elder) {
        elder.setId(id);
        elderService.updateById(elder);
        return Result.success();
    }

    @Operation(summary = "退住办理")
    @PostMapping("/{id}/checkout")
    public Result<Void> checkOut(@PathVariable Long id) {
        elderService.checkOut(id);
        return Result.success();
    }
}
