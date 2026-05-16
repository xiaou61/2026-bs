package com.esgreport.controller;

import com.esgreport.common.Result;
import com.esgreport.entity.CompanySubmission;
import com.esgreport.service.AuthService;
import com.esgreport.service.CompanySubmissionService;
import com.github.pagehelper.PageInfo;
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
@RequestMapping("/api/submission")
@RequiredArgsConstructor
public class CompanySubmissionController {
    private final AuthService authService;
    private final CompanySubmissionService service;

    @GetMapping("/page")
    public Result<PageInfo<CompanySubmission>> page(@RequestAttribute String role,
                                                    @RequestParam(required = false) Integer pageNum,
                                                    @RequestParam(required = false) Integer pageSize,
                                                    @RequestParam(required = false) String keyword,
                                                    @RequestParam(required = false) String status) {
        authService.assertAdminOrEditorOrReviewerOrEsgManager(role);
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute String role, @RequestBody CompanySubmission entity) {
        authService.assertAdminOrEditor(role);
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute String role, @RequestBody CompanySubmission entity) {
        authService.assertAdminOrEditor(role);
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdmin(role);
        service.delete(id);
        return Result.success();
    }

    @PutMapping("/submit/{id}")
    public Result<Void> submit(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrEditor(role);
        service.updateStatus(id, "SUBMITTED");
        return Result.success();
    }

    @PutMapping("/approve/{id}")
    public Result<Void> approve(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrReviewer(role);
        service.updateStatus(id, "APPROVED");
        return Result.success();
    }
}
