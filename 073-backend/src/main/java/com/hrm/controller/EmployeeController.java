package com.hrm.controller;

import com.hrm.common.Result;
import com.hrm.entity.Employee;
import com.hrm.service.EmployeeService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
    @Resource
    private EmployeeService employeeService;

    @GetMapping("/{id}")
    public Result getById(@PathVariable Long id) {
        return Result.success(employeeService.getById(id));
    }

    @GetMapping("/list")
    public Result getList(@RequestParam(required = false) String name,
                          @RequestParam(required = false) String empNo,
                          @RequestParam(required = false) Long departmentId,
                          @RequestParam(required = false) String status,
                          @RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(employeeService.getList(name, empNo, departmentId, status, pageNum, pageSize));
    }

    @GetMapping("/all")
    public Result getAll() {
        return Result.success(employeeService.getAll());
    }

    @GetMapping("/statistics")
    public Result getStatistics() {
        return Result.success(employeeService.getStatistics());
    }

    @PostMapping
    public Result add(@RequestBody Employee employee) {
        employeeService.add(employee);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody Employee employee) {
        employeeService.update(employee);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        employeeService.delete(id);
        return Result.success();
    }
}
