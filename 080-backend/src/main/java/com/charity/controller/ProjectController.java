package com.charity.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.charity.common.Result;
import com.charity.entity.Project;
import com.charity.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping("/list")
    public Result<Page<Project>> getList(@RequestParam(defaultValue = "1") int pageNum,
                                         @RequestParam(defaultValue = "10") int pageSize,
                                         @RequestParam(required = false) Integer projectStatus) {
        Page<Project> page = projectService.getList(pageNum, pageSize, projectStatus);
        return Result.success(page);
    }

    @PostMapping("/add")
    public Result<String> add(@RequestBody Project project) {
        projectService.add(project);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<String> update(@RequestBody Project project) {
        projectService.update(project);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id) {
        projectService.delete(id);
        return Result.success();
    }
}
