package com.xiaou.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.common.Result;
import com.xiaou.entity.ExpressCompany;
import com.xiaou.service.ExpressCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/company")
@CrossOrigin
public class ExpressCompanyController {

    @Autowired
    private ExpressCompanyService companyService;

    @GetMapping("/list")
    public Result<?> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) Integer status) {
        
        Page<ExpressCompany> pageObj = new Page<>(page, size);
        QueryWrapper<ExpressCompany> wrapper = new QueryWrapper<>();
        
        if (status != null) {
            wrapper.eq("status", status);
        }
        
        wrapper.orderByAsc("sort_order");
        Page<ExpressCompany> result = companyService.page(pageObj, wrapper);
        
        return Result.success(result);
    }

    @GetMapping("/all")
    public Result<?> all() {
        QueryWrapper<ExpressCompany> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 1);
        wrapper.orderByAsc("sort_order");
        return Result.success(companyService.list(wrapper));
    }

    @GetMapping("/{id}")
    public Result<?> detail(@PathVariable Long id) {
        ExpressCompany company = companyService.getById(id);
        if (company == null) {
            return Result.error("快递公司不存在");
        }
        return Result.success(company);
    }

    @PostMapping
    public Result<?> add(@RequestBody ExpressCompany company) {
        company.setStatus(1);
        companyService.save(company);
        return Result.success("添加成功");
    }

    @PutMapping("/{id}")
    public Result<?> update(@PathVariable Long id, @RequestBody ExpressCompany company) {
        ExpressCompany dbCompany = companyService.getById(id);
        if (dbCompany == null) {
            return Result.error("快递公司不存在");
        }
        company.setId(id);
        companyService.updateById(company);
        return Result.success("更新成功");
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        companyService.removeById(id);
        return Result.success("删除成功");
    }
}

