package com.hrm.controller;

import com.hrm.common.BusinessException;
import com.hrm.common.Result;
import com.hrm.entity.Salary;
import com.hrm.entity.User;
import com.hrm.service.SalaryService;
import com.hrm.service.UserService;
import com.hrm.utils.AuthUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/salary")
public class SalaryController {
    @Resource
    private SalaryService salaryService;
    @Resource
    private UserService userService;

    @GetMapping("/{id}")
    public Result getById(@PathVariable Long id, HttpServletRequest request) {
        Salary salary = salaryService.getById(id);
        requireOwnSalaryOrHr(request, salary);
        return Result.success(salary);
    }

    @GetMapping("/list")
    public Result getList(@RequestParam(required = false) Long employeeId,
                          @RequestParam(required = false) String employeeName,
                          @RequestParam(required = false) String yearMonth,
                          @RequestParam(required = false) String status,
                          @RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          HttpServletRequest request) {
        if (!AuthUtils.isAdminOrHr(request)) {
            employeeId = currentEmployeeId(request);
            employeeName = null;
        }
        return Result.success(salaryService.getList(employeeId, employeeName, yearMonth, status, pageNum, pageSize));
    }

    @PostMapping
    public Result add(@RequestBody Salary salary, HttpServletRequest request) {
        AuthUtils.requireAdminOrHr(request);
        salaryService.add(salary);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody Salary salary, HttpServletRequest request) {
        AuthUtils.requireAdminOrHr(request);
        salaryService.update(salary);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id, HttpServletRequest request) {
        AuthUtils.requireAdminOrHr(request);
        salaryService.delete(id);
        return Result.success();
    }

    @PostMapping("/pay/{id}")
    public Result pay(@PathVariable Long id, HttpServletRequest request) {
        AuthUtils.requireAdminOrHr(request);
        salaryService.pay(id);
        return Result.success();
    }

    private Long currentEmployeeId(HttpServletRequest request) {
        User user = userService.getById(AuthUtils.currentUserId(request));
        if (user == null || user.getEmployeeId() == null) {
            throw new BusinessException(403, "当前账号未绑定员工档案");
        }
        return user.getEmployeeId();
    }

    private void requireOwnSalaryOrHr(HttpServletRequest request, Salary salary) {
        if (salary == null) {
            throw new BusinessException(404, "薪资记录不存在");
        }
        if (!AuthUtils.isAdminOrHr(request) && !salary.getEmployeeId().equals(currentEmployeeId(request))) {
            throw new BusinessException(403, "只能访问自己的薪资记录");
        }
    }
}
