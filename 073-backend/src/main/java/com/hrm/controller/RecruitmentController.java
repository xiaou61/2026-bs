package com.hrm.controller;

import com.hrm.common.Result;
import com.hrm.entity.Recruitment;
import com.hrm.service.RecruitmentService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

@RestController
@RequestMapping("/api/recruitment")
public class RecruitmentController {
    @Resource
    private RecruitmentService recruitmentService;

    @GetMapping("/{id}")
    public Result getById(@PathVariable Long id) {
        return Result.success(recruitmentService.getById(id));
    }

    @GetMapping("/list")
    public Result getList(@RequestParam(required = false) Long positionId,
                          @RequestParam(required = false) Long departmentId,
                          @RequestParam(required = false) String status,
                          @RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(recruitmentService.getList(positionId, departmentId, status, pageNum, pageSize));
    }

    @GetMapping("/open")
    public Result getOpen() {
        return Result.success(recruitmentService.getOpen());
    }

    @GetMapping("/openCount")
    public Result getOpenCount() {
        return Result.success(recruitmentService.getOpenCount());
    }

    @PostMapping
    public Result add(@RequestBody Recruitment recruitment) {
        recruitmentService.add(recruitment);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody Recruitment recruitment) {
        recruitmentService.update(recruitment);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        recruitmentService.delete(id);
        return Result.success();
    }

    @PostMapping("/close/{id}")
    public Result close(@PathVariable Long id) {
        recruitmentService.close(id);
        return Result.success();
    }
}
