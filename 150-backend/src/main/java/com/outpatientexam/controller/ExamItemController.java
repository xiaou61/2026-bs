package com.outpatientexam.controller;

import com.github.pagehelper.PageInfo;
import com.outpatientexam.common.Result;
import com.outpatientexam.entity.ExamItem;
import com.outpatientexam.service.AuthService;
import com.outpatientexam.service.ExamItemService;
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
@RequestMapping("/api/item")
@RequiredArgsConstructor
public class ExamItemController {
    private final AuthService authService;
    private final ExamItemService service;

    @GetMapping("/page")
    public Result<PageInfo<ExamItem>> page(@RequestAttribute("role") String role,
                                           @RequestParam(required = false) Integer pageNum,
                                           @RequestParam(required = false) Integer pageSize,
                                           @RequestParam(required = false) String keyword,
                                           @RequestParam(required = false) String status) {
        authService.assertAnyRole(role, "ADMIN", "TECHNICIAN");
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute("role") String role, @RequestBody ExamItem entity) {
        authService.assertAnyRole(role, "ADMIN", "TECHNICIAN");
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute("role") String role, @RequestBody ExamItem entity) {
        authService.assertAnyRole(role, "ADMIN", "TECHNICIAN");
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute("role") String role, @PathVariable Long id) {
        authService.assertAnyRole(role, "ADMIN", "TECHNICIAN");
        service.delete(id);
        return Result.success();
    }

    @PutMapping("/activate/{id}")
    public Result<Void> activate(@RequestAttribute("role") String role, @PathVariable Long id) {
        authService.assertAnyRole(role, "ADMIN", "TECHNICIAN");
        service.updateStatus(id, "ACTIVE");
        return Result.success();
    }

    @PutMapping("/finish/{id}")
    public Result<Void> finish(@RequestAttribute("role") String role, @PathVariable Long id) {
        authService.assertAnyRole(role, "ADMIN", "TECHNICIAN");
        service.updateStatus(id, "FINISHED");
        return Result.success();
    }
}
