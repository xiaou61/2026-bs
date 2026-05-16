package com.esgreport.controller;

import com.esgreport.common.Result;
import com.esgreport.entity.IndicatorLibrary;
import com.esgreport.service.AuthService;
import com.esgreport.service.IndicatorLibraryService;
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
@RequestMapping("/api/indicator")
@RequiredArgsConstructor
public class IndicatorLibraryController {
    private final AuthService authService;
    private final IndicatorLibraryService service;

    @GetMapping("/page")
    public Result<PageInfo<IndicatorLibrary>> page(@RequestAttribute String role,
                                                   @RequestParam(required = false) Integer pageNum,
                                                   @RequestParam(required = false) Integer pageSize,
                                                   @RequestParam(required = false) String keyword,
                                                   @RequestParam(required = false) String status) {
        authService.assertAdminOrEditorOrReviewerOrEsgManager(role);
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute String role, @RequestBody IndicatorLibrary entity) {
        authService.assertAdminOrEditorOrEsgManager(role);
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute String role, @RequestBody IndicatorLibrary entity) {
        authService.assertAdminOrEditorOrEsgManager(role);
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
        authService.assertAdminOrEsgManager(role);
        service.updateStatus(id, "ACTIVE");
        return Result.success();
    }

    @PutMapping("/finish/{id}")
    public Result<Void> finish(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrEsgManager(role);
        service.updateStatus(id, "FINISHED");
        return Result.success();
    }
}
