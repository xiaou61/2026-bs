package com.hrm.controller;

import com.hrm.common.Result;
import com.hrm.entity.Salary;
import com.hrm.service.SalaryService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

@RestController
@RequestMapping("/api/salary")
public class SalaryController {
    @Resource
    private SalaryService salaryService;

    @GetMapping("/{id}")
    public Result getById(@PathVariable Long id) {
        return Result.success(salaryService.getById(id));
    }

    @GetMapping("/list")
    public Result getList(@RequestParam(required = false) Long employeeId,
                          @RequestParam(required = false) String employeeName,
                          @RequestParam(required = false) String yearMonth,
                          @RequestParam(required = false) String status,
                          @RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(salaryService.getList(employeeId, employeeName, yearMonth, status, pageNum, pageSize));
    }

    @PostMapping
    public Result add(@RequestBody Salary salary) {
        salaryService.add(salary);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody Salary salary) {
        salaryService.update(salary);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        salaryService.delete(id);
        return Result.success();
    }

    @PostMapping("/pay/{id}")
    public Result pay(@PathVariable Long id) {
        salaryService.pay(id);
        return Result.success();
    }
}
