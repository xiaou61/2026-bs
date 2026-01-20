package com.xiaou.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaou.common.Result;
import com.xiaou.entity.Provider;
import com.xiaou.entity.ProviderServiceEntity;
import com.xiaou.service.ProviderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "服务商管理")
@RestController
@RequestMapping("/api/provider")
@RequiredArgsConstructor
public class ProviderController {

    private final ProviderService providerService;

    @Operation(summary = "分页获取服务商列表")
    @GetMapping("/page")
    public Result<IPage<Provider>> page(@RequestParam(defaultValue = "1") Integer current,
                                        @RequestParam(defaultValue = "10") Integer size,
                                        @RequestParam(required = false) String keyword) {
        return Result.success(providerService.page(current, size, keyword));
    }

    @Operation(summary = "获取服务商详情")
    @GetMapping("/{id}")
    public Result<Provider> detail(@PathVariable Long id) {
        return Result.success(providerService.detail(id));
    }

    @Operation(summary = "获取服务商的服务列表")
    @GetMapping("/{id}/services")
    public Result<List<ProviderServiceEntity>> services(@PathVariable Long id) {
        return Result.success(providerService.listServices(id));
    }
}
