package com.hrm.controller;

import com.hrm.common.Result;
import com.hrm.entity.Employee;
import com.hrm.service.EmployeeService;
import com.hrm.utils.AuthUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
    @Resource
    private EmployeeService employeeService;

    @GetMapping("/{id}")
    public Result getById(@PathVariable Long id, HttpServletRequest request) {
        AuthUtils.requireAdminOrHr(request);
        return Result.success(employeeService.getById(id));
    }

    @GetMapping("/list")
    public Result getList(@RequestParam(required = false) String name,
                          @RequestParam(required = false) String empNo,
                          @RequestParam(required = false) Long departmentId,
                          @RequestParam(required = false) String status,
                          @RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          HttpServletRequest request) {
        AuthUtils.requireAdminOrHr(request);
        return Result.success(employeeService.getList(name, empNo, departmentId, status, pageNum, pageSize));
    }

    @GetMapping("/all")
    public Result getAll(HttpServletRequest request) {
        AuthUtils.requireAdminOrHr(request);
        return Result.success(employeeService.getAll());
    }

    @GetMapping("/statistics")
    public Result getStatistics(HttpServletRequest request) {
        AuthUtils.requireAdminOrHr(request);
        return Result.success(employeeService.getStatistics());
    }

    @PostMapping
    public Result add(@RequestBody Employee employee, HttpServletRequest request) {
        AuthUtils.requireAdminOrHr(request);
        employeeService.add(employee);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody Employee employee, HttpServletRequest request) {
        AuthUtils.requireAdminOrHr(request);
        employeeService.update(employee);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id, HttpServletRequest request) {
        AuthUtils.requireAdminOrHr(request);
        employeeService.delete(id);
        return Result.success();
    }
}
