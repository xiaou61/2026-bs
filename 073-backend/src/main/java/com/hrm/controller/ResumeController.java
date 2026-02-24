package com.hrm.controller;

import com.hrm.common.Result;
import com.hrm.entity.Resume;
import com.hrm.service.ResumeService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/api/resume")
public class ResumeController {
    @Resource
    private ResumeService resumeService;

    @GetMapping("/{id}")
    public Result getById(@PathVariable Long id) {
        return Result.success(resumeService.getById(id));
    }

    @GetMapping("/list")
    public Result getList(@RequestParam(required = false) Long recruitmentId,
                          @RequestParam(required = false) String name,
                          @RequestParam(required = false) String status,
                          @RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(resumeService.getList(recruitmentId, name, status, pageNum, pageSize));
    }

    @PostMapping
    public Result add(@RequestBody Resume resume) {
        resumeService.add(resume);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody Resume resume) {
        resumeService.update(resume);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        resumeService.delete(id);
        return Result.success();
    }

    @PostMapping("/updateStatus")
    public Result updateStatus(@RequestBody Map<String, Object> params) {
        Long id = Long.valueOf(params.get("id").toString());
        String status = params.get("status").toString();
        resumeService.updateStatus(id, status);
        return Result.success();
    }
}
