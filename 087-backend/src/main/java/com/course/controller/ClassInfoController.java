package com.course.controller;

import com.github.pagehelper.PageInfo;
import com.course.common.Result;
import com.course.entity.ClassInfo;
import com.course.service.ClassInfoService;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.util.List;

@RestController
@RequestMapping("/api/class")
public class ClassInfoController {

    @Autowired
    private ClassInfoService classInfoService;

    @GetMapping("/list")
    public Result<PageInfo<ClassInfo>> list(@RequestParam(defaultValue = "1") Integer pageNum,
                                            @RequestParam(defaultValue = "10") Integer pageSize,
                                            @RequestParam(required = false) String name,
                                            @RequestParam(required = false) Long majorId,
                                            @RequestParam(required = false) Integer status) {
        return Result.success(classInfoService.list(name, majorId, status, pageNum, pageSize));
    }

    @GetMapping("/enabled")
    public Result<List<ClassInfo>> enabled() {
        return Result.success(classInfoService.enabledList());
    }

    @PostMapping("/add")
    public Result<String> add(@RequestBody ClassInfo entity, @RequestAttribute("role") String role) {
        classInfoService.add(entity, role);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<String> update(@RequestBody ClassInfo entity, @RequestAttribute("role") String role) {
        classInfoService.update(entity, role);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id, @RequestAttribute("role") String role) {
        classInfoService.delete(id, role);
        return Result.success();
    }
}
