package com.licensecheck.controller;

import com.licensecheck.common.Result;
import com.licensecheck.entity.RiskIssue;
import com.licensecheck.service.RiskIssueService;
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
@RequestMapping("/api/issue")
@RequiredArgsConstructor
public class RiskIssueController {
    private final RiskIssueService service;

    @GetMapping("/page")
    public Result<PageInfo<RiskIssue>> page(@RequestParam(required = false) Integer pageNum,
                                        @RequestParam(required = false) Integer pageSize,
                                        @RequestParam(required = false) String keyword,
                                        @RequestParam(required = false) String status) {
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }


    @PostMapping
    public Result<Void> add(@RequestBody RiskIssue entity) {
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestBody RiskIssue entity) {
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return Result.success();
    }



    @PutMapping("/confirm/{id}")
    public Result<Void> confirm(@PathVariable Long id) {
        service.updateStatus(id, "CONFIRMED");
        return Result.success();
    }


    @PutMapping("/ignore/{id}")
    public Result<Void> ignore(@PathVariable Long id) {
        service.updateStatus(id, "IGNORED");
        return Result.success();
    }

}
