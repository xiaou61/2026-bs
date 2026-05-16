package com.phishingtraining.controller;

import com.phishingtraining.common.Result;
import com.phishingtraining.entity.TrainingCourse;
import com.phishingtraining.service.AuthService;
import com.phishingtraining.service.TrainingCourseService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/course")
@RequiredArgsConstructor
public class TrainingCourseController {
    private final AuthService authService;
    private final TrainingCourseService service;

    @GetMapping("/page")
    public Result<IPage<TrainingCourse>> page(@RequestAttribute String role,
                                            @RequestParam(required = false) Integer pageNum,
                                            @RequestParam(required = false) Integer pageSize,
                                            @RequestParam(required = false) String keyword,
                                            @RequestParam(required = false) String status) {
        authService.assertAdminOrTrainerOrAuditor(role);
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute String role, @RequestBody TrainingCourse entity) {
        authService.assertAdminOrTrainer(role);
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute String role, @RequestBody TrainingCourse entity) {
        authService.assertAdminOrTrainer(role);
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdmin(role);
        service.delete(id);
        return Result.success();
    }

    @PutMapping("/publish/{id}")
    public Result<Void> publish(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrTrainer(role);
        service.updateStatus(id, "PUBLISHED");
        return Result.success();
    }

    @PutMapping("/archive/{id}")
    public Result<Void> archive(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrTrainer(role);
        service.updateStatus(id, "ARCHIVED");
        return Result.success();
    }

}
