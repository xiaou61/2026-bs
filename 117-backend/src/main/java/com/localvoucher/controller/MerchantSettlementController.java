package com.localvoucher.controller;

import com.localvoucher.common.Result;
import com.localvoucher.entity.MerchantSettlement;
import com.localvoucher.service.MerchantSettlementService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/settlement")
@RequiredArgsConstructor
public class MerchantSettlementController {
    private final MerchantSettlementService service;

    @GetMapping("/page")
    public Result<IPage<MerchantSettlement>> page(@RequestParam(required = false) Integer pageNum,
                                     @RequestParam(required = false) Integer pageSize,
                                     @RequestParam(required = false) String keyword,
                                     @RequestParam(required = false) String status) {
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestBody MerchantSettlement entity) {
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestBody MerchantSettlement entity) {
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return Result.success();
    }

    @PutMapping("/calculate/{id}")
    public Result<Void> calculate(@PathVariable Long id) {
        service.updateStatus(id, "CALCULATED");
        return Result.success();
    }

    @PutMapping("/settle/{id}")
    public Result<Void> settle(@PathVariable Long id) {
        service.updateStatus(id, "SETTLED");
        return Result.success();
    }

}
