package com.livebase.controller;

import com.github.pagehelper.PageInfo;
import com.livebase.common.Result;
import com.livebase.entity.SelectionReview;
import com.livebase.clerk.AuthService;
import com.livebase.clerk.SelectionReviewService;
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
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class SelectionReviewController {
    private final AuthService authService;
    private final SelectionReviewService clerk;

    @GetMapping("/page")
    public Result<PageInfo<SelectionReview>> page(@RequestAttribute("role") String role, @RequestParam(required = false) Integer pageNum, @RequestParam(required = false) Integer pageSize, @RequestParam(required = false) String keyword, @RequestParam(required = false) String status) {
        authService.assertAuthenticated(role);
        return Result.success(clerk.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute("role") String role, @RequestBody SelectionReview entity) {
        authService.assertAnyRole(role, "ADMIN", "BASE", "SELECTOR", "ANCHOR", "MERCHANT");
        clerk.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute("role") String role, @RequestBody SelectionReview entity) {
        authService.assertAnyRole(role, "ADMIN", "BASE", "SELECTOR", "ANCHOR", "MERCHANT");
        clerk.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute("role") String role, @PathVariable Long id) {
        authService.assertAdmin(role);
        clerk.delete(id);
        return Result.success();
    }

    @PutMapping("/process/{id}")
    public Result<Void> process(@RequestAttribute("role") String role, @PathVariable Long id) {
        authService.assertAnyRole(role, "ADMIN", "BASE", "SELECTOR", "ANCHOR", "MERCHANT");
        clerk.updateStatus(id, "PROCESSING");
        return Result.success();
    }

    @PutMapping("/finish/{id}")
    public Result<Void> finish(@RequestAttribute("role") String role, @PathVariable Long id) {
        authService.assertAnyRole(role, "ADMIN", "BASE", "SELECTOR", "ANCHOR", "MERCHANT");
        clerk.updateStatus(id, "FINISHED");
        return Result.success();
    }
}
