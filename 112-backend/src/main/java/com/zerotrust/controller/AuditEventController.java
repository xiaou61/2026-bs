package com.zerotrust.controller;

import com.zerotrust.common.Result;
import com.zerotrust.entity.AuditEvent;
import com.zerotrust.service.AuditEventService;
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
@RequestMapping("/api/audit-event")
@RequiredArgsConstructor
public class AuditEventController {
    private final AuditEventService service;

    @GetMapping("/page")
    public Result<PageInfo<AuditEvent>> page(@RequestParam(required = false) Integer pageNum,
                                        @RequestParam(required = false) Integer pageSize,
                                        @RequestParam(required = false) String keyword,
                                        @RequestParam(required = false) String status) {
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestBody AuditEvent entity) {
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestBody AuditEvent entity) {
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return Result.success();
    }

    @PutMapping("/ack/{id}")
    public Result<Void> ack(@PathVariable Long id) {
        service.updateStatus(id, "ACKED");
        return Result.success();
    }

    @PutMapping("/close/{id}")
    public Result<Void> close(@PathVariable Long id) {
        service.updateStatus(id, "CLOSED");
        return Result.success();
    }

}
