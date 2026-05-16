package com.conferencereview.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.conferencereview.common.Result;
import com.conferencereview.entity.BlindReview;
import com.conferencereview.service.AuthService;
import com.conferencereview.service.BlindReviewService;
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
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class BlindReviewController {
    private final AuthService authService;
    private final BlindReviewService service;

    @GetMapping("/page")
    public Result<IPage<BlindReview>> page(@RequestAttribute String role,
                                           @RequestParam(required = false) Integer pageNum,
                                           @RequestParam(required = false) Integer pageSize,
                                           @RequestParam(required = false) String keyword,
                                           @RequestParam(required = false) String status) {
        authService.assertAdminOrReviewer(role);
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute String role, @RequestBody BlindReview entity) {
        authService.assertAdminOrReviewer(role);
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute String role, @RequestBody BlindReview entity) {
        authService.assertAdminOrReviewer(role);
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdmin(role);
        service.delete(id);
        return Result.success();
    }

    @PutMapping("/process/{id}")
    public Result<Void> process(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrReviewer(role);
        service.updateStatus(id, "PROCESSING");
        return Result.success();
    }

    @PutMapping("/finish/{id}")
    public Result<Void> finish(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrReviewer(role);
        service.updateStatus(id, "FINISHED");
        return Result.success();
    }

}

