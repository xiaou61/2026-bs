package com.student.controller;

import com.student.common.BusinessException;
import com.student.common.Result;
import com.student.entity.Job;
import com.student.service.JobService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/job")
public class JobController {

    @Resource
    private JobService jobService;

    @GetMapping("/list")
    public Result<?> list(@RequestParam(required = false) Integer status) {
        return Result.success(jobService.list(status));
    }

    @GetMapping("/page")
    public Result<?> page(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) String keyword,
                          @RequestParam(required = false) Integer status,
                          @RequestParam(required = false) Long publisherId,
                          HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        String role = (String) request.getAttribute("role");
        return Result.success(jobService.page(pageNum, pageSize, keyword, status, publisherId, role, userId));
    }

    @PostMapping
    public Result<?> add(@RequestBody Job job, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        String role = (String) request.getAttribute("role");
        checkStaff(role);
        jobService.save(job, role, userId);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody Job job, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        String role = (String) request.getAttribute("role");
        checkStaff(role);
        jobService.save(job, role, userId);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        String role = (String) request.getAttribute("role");
        checkStaff(role);
        jobService.deleteById(id, role, userId);
        return Result.success();
    }

    private void checkStaff(String role) {
        if (!"ADMIN".equals(role) && !"TEACHER".equals(role)) {
            throw new BusinessException("无权限操作");
        }
    }
}
