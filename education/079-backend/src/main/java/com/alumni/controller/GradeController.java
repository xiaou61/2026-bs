package com.alumni.controller;

import com.alumni.common.Result;
import com.alumni.entity.Grade;
import com.alumni.service.GradeService;
import com.alumni.utils.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/grade")
public class GradeController {

    @Autowired
    private GradeService gradeService;

    @GetMapping("/list")
    public Result<List<Grade>> list() {
        return Result.success(gradeService.list());
    }

    @PostMapping
    public Result<?> add(@RequestBody Grade grade, HttpServletRequest request) {
        AuthUtils.requireAdmin(request);
        gradeService.add(grade);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody Grade grade, HttpServletRequest request) {
        AuthUtils.requireAdmin(request);
        gradeService.update(grade);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, HttpServletRequest request) {
        AuthUtils.requireAdmin(request);
        gradeService.delete(id);
        return Result.success();
    }
}
