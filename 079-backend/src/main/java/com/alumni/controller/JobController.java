package com.alumni.controller;

import com.alumni.common.Result;
import com.alumni.entity.JobPost;
import com.alumni.service.JobService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/job")
public class JobController {

    @Autowired
    private JobService jobService;

    @GetMapping("/list")
    public Result<Page<JobPost>> list(@RequestParam(defaultValue = "1") Integer pageNum,
                                      @RequestParam(defaultValue = "10") Integer pageSize,
                                      String title, String city, Integer status) {
        return Result.success(jobService.list(pageNum, pageSize, title, city, status));
    }

    @GetMapping("/{id}")
    public Result<JobPost> getById(@PathVariable Long id) {
        return Result.success(jobService.getById(id));
    }

    @PostMapping
    public Result<?> add(@RequestBody JobPost job) {
        jobService.add(job);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody JobPost job) {
        jobService.update(job);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        jobService.delete(id);
        return Result.success();
    }

    @PutMapping("/{id}/audit")
    public Result<?> audit(@PathVariable Long id, @RequestBody Map<String, Integer> params) {
        jobService.audit(id, params.get("status"));
        return Result.success();
    }
}
