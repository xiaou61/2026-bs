package com.xiaou.recruitment.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaou.recruitment.common.Result;
import com.xiaou.recruitment.entity.Job;
import com.xiaou.recruitment.service.JobService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/job")
public class JobController {

    @Autowired
    private JobService jobService;

    @PostMapping("/publish")
    public Result<?> publishJob(@RequestBody Job job, HttpServletRequest request) {
        if (jobService.publishJob(job)) {
            return Result.success(job);
        }
        return Result.error("发布失败");
    }

    @PutMapping("/update")
    public Result<?> updateJob(@RequestBody Job job, HttpServletRequest request) {
        if (jobService.updateJob(job)) {
            return Result.success(job);
        }
        return Result.error("更新失败");
    }

    @DeleteMapping("/{id}")
    public Result<?> deleteJob(@PathVariable Long id, HttpServletRequest request) {
        if (jobService.removeById(id)) {
            return Result.success();
        }
        return Result.error("删除失败");
    }

    @GetMapping("/{id}")
    public Result<?> getJobById(@PathVariable Long id) {
        Job job = jobService.getJobById(id);
        if (job != null) {
            return Result.success(job);
        }
        return Result.error("岗位不存在");
    }

    @GetMapping("/list")
    public Result<?> getJobList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String jobType,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String keyword) {
        IPage<Job> jobPage = jobService.getJobList(page, size, jobType, category, location, keyword);
        return Result.success(jobPage);
    }

    @GetMapping("/recommend")
    public Result<?> getRecommendJobs(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        List<Job> jobs = jobService.getRecommendJobs(userId);
        return Result.success(jobs);
    }

    @GetMapping("/company/{companyId}")
    public Result<?> getJobsByCompanyId(
            @PathVariable Long companyId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        IPage<Job> jobPage = jobService.getJobsByCompanyId(page, size, companyId);
        return Result.success(jobPage);
    }
}
