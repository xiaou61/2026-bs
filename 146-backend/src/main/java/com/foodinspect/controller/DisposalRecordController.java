package com.foodinspect.controller;

import com.github.pagehelper.PageInfo;
import com.foodinspect.common.Result;
import com.foodinspect.entity.DisposalRecord;
import com.foodinspect.service.AuthService;
import com.foodinspect.service.DisposalRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/disposal")
@RequiredArgsConstructor
public class DisposalRecordController {
    private final AuthService authService;
    private final DisposalRecordService service;

    @GetMapping("/page")
    public Result<PageInfo<DisposalRecord>> page(@RequestAttribute String role,
                                                 @RequestParam(required = false) Integer pageNum,
                                                 @RequestParam(required = false) Integer pageSize,
                                                 @RequestParam(required = false) String keyword,
                                                 @RequestParam(required = false) String status) {
        authService.assertAnyRole(role, "ADMIN", "REVIEWER");
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute String role, @RequestBody DisposalRecord entity) {
        authService.assertAnyRole(role, "ADMIN", "REVIEWER");
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute String role, @RequestBody DisposalRecord entity) {
        authService.assertAnyRole(role, "ADMIN", "REVIEWER");
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdmin(role);
        service.delete(id);
        return Result.success();
    }

    @PutMapping("/activate/{id}")
    public Result<Void> activate(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAnyRole(role, "ADMIN", "REVIEWER");
        service.updateStatus(id, "ACTIVE");
        return Result.success();
    }

    @PutMapping("/finish/{id}")
    public Result<Void> finish(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAnyRole(role, "ADMIN", "REVIEWER");
        service.updateStatus(id, "FINISHED");
        return Result.success();
    }
}
