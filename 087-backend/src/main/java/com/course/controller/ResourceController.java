package com.course.controller;

import com.github.pagehelper.PageInfo;
import com.course.common.Result;
import com.course.entity.CourseResource;
import com.course.service.ResourceService;
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

@RestController
@RequestMapping("/api/resource")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @GetMapping("/list")
    public Result<PageInfo<CourseResource>> list(@RequestParam(defaultValue = "1") Integer pageNum,
                                                 @RequestParam(defaultValue = "10") Integer pageSize,
                                                 @RequestParam(required = false) Long scheduleId,
                                                 @RequestParam(required = false) String keyword,
                                                 @RequestAttribute("userId") Long userId,
                                                 @RequestAttribute("role") String role) {
        return Result.success(resourceService.list(scheduleId, keyword, userId, role, pageNum, pageSize));
    }

    @PostMapping("/add")
    public Result<String> add(@RequestBody CourseResource entity,
                              @RequestAttribute("userId") Long userId,
                              @RequestAttribute("role") String role) {
        resourceService.add(entity, userId, role);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<String> update(@RequestBody CourseResource entity, @RequestAttribute("role") String role) {
        resourceService.update(entity, role);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id, @RequestAttribute("role") String role) {
        resourceService.delete(id, role);
        return Result.success();
    }
}
