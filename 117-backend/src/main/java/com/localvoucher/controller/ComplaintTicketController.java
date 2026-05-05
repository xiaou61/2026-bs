package com.localvoucher.controller;

import com.localvoucher.common.Result;
import com.localvoucher.entity.ComplaintTicket;
import com.localvoucher.service.ComplaintTicketService;
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
@RequestMapping("/api/complaint")
@RequiredArgsConstructor
public class ComplaintTicketController {
    private final ComplaintTicketService service;

    @GetMapping("/page")
    public Result<IPage<ComplaintTicket>> page(@RequestParam(required = false) Integer pageNum,
                                     @RequestParam(required = false) Integer pageSize,
                                     @RequestParam(required = false) String keyword,
                                     @RequestParam(required = false) String status) {
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestBody ComplaintTicket entity) {
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestBody ComplaintTicket entity) {
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return Result.success();
    }

    @PutMapping("/assign/{id}")
    public Result<Void> assign(@PathVariable Long id) {
        service.updateStatus(id, "ASSIGNED");
        return Result.success();
    }

    @PutMapping("/resolve/{id}")
    public Result<Void> resolve(@PathVariable Long id) {
        service.updateStatus(id, "RESOLVED");
        return Result.success();
    }

    @PutMapping("/close/{id}")
    public Result<Void> close(@PathVariable Long id) {
        service.updateStatus(id, "CLOSED");
        return Result.success();
    }

}
