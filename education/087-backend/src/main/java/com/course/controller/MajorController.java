package com.course.controller;

import com.github.pagehelper.PageInfo;
import com.course.common.Result;
import com.course.entity.MajorInfo;
import com.course.service.MajorService;
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
@RequestMapping("/api/major")
public class MajorController {

    @Autowired
    private MajorService majorService;

    @GetMapping("/list")
    public Result<PageInfo<MajorInfo>> list(@RequestParam(defaultValue = "1") Integer pageNum,
                                            @RequestParam(defaultValue = "10") Integer pageSize,
                                            @RequestParam(required = false) String name,
                                            @RequestParam(required = false) Long departmentId,
                                            @RequestParam(required = false) Integer status) {
        return Result.success(majorService.list(name, departmentId, status, pageNum, pageSize));
    }

    @GetMapping("/enabled")
    public Result<List<MajorInfo>> enabled() {
        return Result.success(majorService.enabledList());
    }

    @PostMapping("/add")
    public Result<String> add(@RequestBody MajorInfo entity, @RequestAttribute("role") String role) {
        majorService.add(entity, role);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<String> update(@RequestBody MajorInfo entity, @RequestAttribute("role") String role) {
        majorService.update(entity, role);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id, @RequestAttribute("role") String role) {
        majorService.delete(id, role);
        return Result.success();
    }
}
