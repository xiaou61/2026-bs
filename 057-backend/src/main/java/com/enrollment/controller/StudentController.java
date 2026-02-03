package com.enrollment.controller;

import com.enrollment.common.Result;
import com.enrollment.entity.Student;
import com.enrollment.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/page")
    public Result<?> getPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             @RequestParam(required = false) String name,
                             @RequestParam(required = false) String examNo,
                             @RequestParam(required = false) Integer status) {
        return Result.success(studentService.getPage(pageNum, pageSize, name, examNo, status));
    }

    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Long id) {
        return Result.success(studentService.getById(id));
    }

    @GetMapping("/list")
    public Result<?> getAll() {
        return Result.success(studentService.getAll());
    }

    @PostMapping
    public Result<?> add(@RequestBody Student student) {
        studentService.add(student);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody Student student) {
        studentService.update(student);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        studentService.delete(id);
        return Result.success();
    }

    @PutMapping("/audit/{id}")
    public Result<?> audit(@PathVariable Long id, @RequestParam Integer status) {
        studentService.audit(id, status);
        return Result.success();
    }
}
