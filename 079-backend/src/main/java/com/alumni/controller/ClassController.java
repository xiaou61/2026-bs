package com.alumni.controller;

import com.alumni.common.Result;
import com.alumni.entity.ClassInfo;
import com.alumni.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/class")
public class ClassController {

    @Autowired
    private ClassService classService;

    @GetMapping("/list")
    public Result<List<ClassInfo>> list(Long gradeId) {
        return Result.success(classService.list(gradeId));
    }

    @PostMapping
    public Result<?> add(@RequestBody ClassInfo classInfo) {
        classService.add(classInfo);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody ClassInfo classInfo) {
        classService.update(classInfo);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        classService.delete(id);
        return Result.success();
    }
}
