package com.examguard.controller;

import com.github.pagehelper.PageInfo;
import com.examguard.common.Result;
import com.examguard.entity.InvigilatorProfile;
import com.examguard.service.AuthService;
import com.examguard.service.InvigilatorProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/invigilator")
@RequiredArgsConstructor
public class InvigilatorProfileController {
    private final AuthService authService;
    private final InvigilatorProfileService service;

    @GetMapping("/page")
    public Result<PageInfo<InvigilatorProfile>> page(@RequestAttribute String role,
                                                     @RequestParam(required = false) Integer pageNum,
                                                     @RequestParam(required = false) Integer pageSize,
                                                     @RequestParam(required = false) String keyword,
                                                     @RequestParam(required = false) String status) {
        authService.assertAdminOrInvigilator(role);
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute String role, @RequestBody InvigilatorProfile entity) {
        authService.assertAdminOrInvigilator(role);
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute String role, @RequestBody InvigilatorProfile entity) {
        authService.assertAdminOrInvigilator(role);
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
        authService.assertAdmin(role);
        service.updateStatus(id, "ACTIVE");
        return Result.success();
    }

    @PutMapping("/finish/{id}")
    public Result<Void> finish(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdmin(role);
        service.updateStatus(id, "FINISHED");
        return Result.success();
    }

}


