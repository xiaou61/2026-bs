package com.hrm.controller;

import com.hrm.common.Result;
import com.hrm.entity.Training;
import com.hrm.service.TrainingService;
import com.hrm.utils.AuthUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/training")
public class TrainingController {
    @Resource
    private TrainingService trainingService;

    @GetMapping("/{id}")
    public Result getById(@PathVariable Long id) {
        return Result.success(trainingService.getById(id));
    }

    @GetMapping("/list")
    public Result getList(@RequestParam(required = false) String title,
                          @RequestParam(required = false) Integer status,
                          @RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(trainingService.getList(title, status, pageNum, pageSize));
    }

    @PostMapping
    public Result add(@RequestBody Training training, HttpServletRequest request) {
        AuthUtils.requireAdminOrHr(request);
        trainingService.add(training);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody Training training, HttpServletRequest request) {
        AuthUtils.requireAdminOrHr(request);
        trainingService.update(training);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id, HttpServletRequest request) {
        AuthUtils.requireAdminOrHr(request);
        trainingService.delete(id);
        return Result.success();
    }
}
