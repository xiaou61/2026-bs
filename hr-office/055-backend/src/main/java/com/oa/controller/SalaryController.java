package com.oa.controller;

import com.oa.common.Result;
import com.oa.entity.Salary;
import com.oa.service.SalaryService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/salary")
@RequiredArgsConstructor
public class SalaryController {
    private final SalaryService salaryService;

    @GetMapping("/list")
    public Result list(@RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       String yearMonth, String keyword) {
        return Result.success(salaryService.getList(pageNum, pageSize, yearMonth, keyword));
    }

    @GetMapping("/my")
    public Result my(HttpServletRequest request,
                     @RequestParam(defaultValue = "1") Integer pageNum,
                     @RequestParam(defaultValue = "10") Integer pageSize,
                     String yearMonth) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(salaryService.getMyList(userId, pageNum, pageSize, yearMonth));
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
}
