package com.livecommerce.controller;

import com.livecommerce.common.Result;
import com.livecommerce.entity.RefundRecord;
import com.livecommerce.service.RefundRecordService;
import com.github.pagehelper.PageInfo;
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
@RequestMapping("/api/refund")
@RequiredArgsConstructor
public class RefundRecordController {
    private final RefundRecordService service;

    @GetMapping("/page")
    public Result<PageInfo<RefundRecord>> page(@RequestParam(required = false) Integer pageNum,
                                        @RequestParam(required = false) Integer pageSize,
                                        @RequestParam(required = false) String keyword,
                                        @RequestParam(required = false) String status) {
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestBody RefundRecord entity) {
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestBody RefundRecord entity) {
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return Result.success();
    }

    @PutMapping("/approve/{id}")
    public Result<Void> approve(@PathVariable Long id) {
        service.updateStatus(id, "APPROVED");
        return Result.success();
    }

    @PutMapping("/pay/{id}")
    public Result<Void> pay(@PathVariable Long id) {
        service.updateStatus(id, "PAID");
        return Result.success();
    }

    @PutMapping("/reject/{id}")
    public Result<Void> reject(@PathVariable Long id) {
        service.updateStatus(id, "REJECTED");
        return Result.success();
    }

}
