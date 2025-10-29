package com.xiaou.recruitment.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaou.recruitment.common.Result;
import com.xiaou.recruitment.entity.Company;
import com.xiaou.recruitment.service.CompanyService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @PostMapping("/register")
    public Result<?> registerCompany(@RequestBody Company company, HttpServletRequest request) {
        if (companyService.registerCompany(company)) {
            return Result.success(company);
        }
        return Result.error("企业注册失败");
    }

    @GetMapping("/{id}")
    public Result<?> getCompanyById(@PathVariable Long id) {
        Company company = companyService.getCompanyById(id);
        if (company != null) {
            return Result.success(company);
        }
        return Result.error("企业不存在");
    }

    @PutMapping("/update")
    public Result<?> updateCompany(@RequestBody Company company, HttpServletRequest request) {
        if (companyService.updateCompany(company)) {
            return Result.success(company);
        }
        return Result.error("更新失败");
    }

    @GetMapping("/list")
    public Result<?> getCompanyList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) Integer status) {
        IPage<Company> companyPage = companyService.getCompanyList(page, size, status);
        return Result.success(companyPage);
    }
}
