package com.kindergarten.controller;

import com.github.pagehelper.PageInfo;
import com.kindergarten.common.Result;
import com.kindergarten.entity.ChildProfile;
import com.kindergarten.service.ChildService;
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
@RequestMapping("/api/child")
public class ChildController {

    @Autowired
    private ChildService childService;

    @GetMapping("/list")
    public Result<PageInfo<ChildProfile>> list(@RequestParam(defaultValue = "1") Integer pageNum,
                                               @RequestParam(defaultValue = "10") Integer pageSize,
                                               @RequestParam(required = false) Long classId,
                                               @RequestParam(required = false) Long parentId,
                                               @RequestParam(required = false) Integer profileStatus,
                                               @RequestAttribute("role") String role,
                                               @RequestAttribute("userId") Long userId) {
        return Result.success(childService.list(classId, parentId, profileStatus, role, userId, pageNum, pageSize));
    }

    @GetMapping("/mine")
    public Result<List<ChildProfile>> mine(@RequestAttribute("userId") Long userId) {
        return Result.success(childService.myChildren(userId));
    }

    @PostMapping("/add")
    public Result<String> add(@RequestBody ChildProfile entity,
                              @RequestAttribute("role") String role,
                              @RequestAttribute("userId") Long userId) {
        childService.add(entity, role, userId);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<String> update(@RequestBody ChildProfile entity,
                                 @RequestAttribute("role") String role,
                                 @RequestAttribute("userId") Long userId) {
        childService.update(entity, role, userId);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id, @RequestAttribute("role") String role) {
        childService.delete(id, role);
        return Result.success();
    }
}
