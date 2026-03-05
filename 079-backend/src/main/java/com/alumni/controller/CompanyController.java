package com.alumni.controller;

import com.alumni.common.Result;
import com.alumni.entity.AlumniCompany;
import com.alumni.service.CompanyService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping("/list")
    public Result<Page<AlumniCompany>> list(@RequestParam(defaultValue = "1") Integer pageNum,
                                            @RequestParam(defaultValue = "10") Integer pageSize,
                                            String name, String industry, Integer status) {
        return Result.success(companyService.list(pageNum, pageSize, name, industry, status));
    }

    @GetMapping("/{id}")
    public Result<AlumniCompany> getById(@PathVariable Long id) {
        return Result.success(companyService.getById(id));
    }

    @PostMapping
    public Result<?> add(@RequestBody AlumniCompany company, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        company.setUserId(userId);
        companyService.add(company);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody AlumniCompany company) {
        companyService.update(company);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        companyService.delete(id);
        return Result.success();
    }

    @PutMapping("/{id}/audit")
    public Result<?> audit(@PathVariable Long id, @RequestBody Map<String, Integer> params) {
        companyService.audit(id, params.get("status"));
        return Result.success();
    }
}
