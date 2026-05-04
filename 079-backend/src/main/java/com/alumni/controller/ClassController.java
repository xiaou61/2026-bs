package com.alumni.controller;

import com.alumni.common.Result;
import com.alumni.entity.ClassInfo;
import com.alumni.service.ClassService;
import com.alumni.utils.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    public Result<?> add(@RequestBody ClassInfo classInfo, HttpServletRequest request) {
        AuthUtils.requireAdmin(request);
        classService.add(classInfo);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody ClassInfo classInfo, HttpServletRequest request) {
        AuthUtils.requireAdmin(request);
        classService.update(classInfo);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, HttpServletRequest request) {
        AuthUtils.requireAdmin(request);
        classService.delete(id);
        return Result.success();
    }
}
