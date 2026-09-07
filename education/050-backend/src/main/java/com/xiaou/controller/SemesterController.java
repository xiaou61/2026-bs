package com.xiaou.controller;

import com.xiaou.common.Result;
import com.xiaou.entity.Semester;
import com.xiaou.service.SemesterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/semester")
public class SemesterController {

    @Autowired
    private SemesterService semesterService;

    @GetMapping("/list")
    public Result<?> listSemesters() {
        List<Semester> list = semesterService.list();
        return Result.success(list);
    }

    @GetMapping("/current")
    public Result<?> getCurrentSemester() {
        Semester semester = semesterService.getCurrentSemester();
        return Result.success(semester);
    }

    @PutMapping("/setCurrent/{id}")
    public Result<?> setCurrentSemester(@PathVariable Long id) {
        semesterService.setCurrentSemester(id);
        return Result.success("设置成功");
    }

    @PostMapping
    public Result<?> addSemester(@RequestBody Semester semester) {
        semester.setCreateTime(LocalDateTime.now());
        semesterService.save(semester);
        return Result.success("添加成功");
    }

    @PutMapping("/{id}")
    public Result<?> updateSemester(@PathVariable Long id, @RequestBody Semester semester) {
        semester.setId(id);
        semester.setUpdateTime(LocalDateTime.now());
        semesterService.updateById(semester);
        return Result.success("更新成功");
    }

    @DeleteMapping("/{id}")
    public Result<?> deleteSemester(@PathVariable Long id) {
        semesterService.removeById(id);
        return Result.success("删除成功");
    }

    @GetMapping("/{id}")
    public Result<?> getSemesterById(@PathVariable Long id) {
        return Result.success(semesterService.getById(id));
    }
}
