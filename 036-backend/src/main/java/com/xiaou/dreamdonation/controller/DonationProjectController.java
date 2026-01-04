package com.xiaou.dreamdonation.controller;

import com.xiaou.dreamdonation.common.Result;
import com.xiaou.dreamdonation.dto.ProjectCreateDTO;
import com.xiaou.dreamdonation.entity.DonationProject;
import com.xiaou.dreamdonation.service.DonationProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
@CrossOrigin
public class DonationProjectController {
    private final DonationProjectService projectService;

    @PostMapping
    public Result&lt;?&gt; createProject(@Valid @RequestBody ProjectCreateDTO dto,
                                    @RequestHeader(value = "userId", defaultValue = "1") Long userId) {
        try {
            return Result.success(projectService.createProject(dto, userId));
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping
    public Result&lt;Page&lt;DonationProject&gt;&gt; listProjects(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String status) {
        try {
            return Result.success(projectService.listProjects(page, size, category, status));
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Result&lt;?&gt; getProjectById(@PathVariable Long id) {
        try {
            return Result.success(projectService.getProjectById(id));
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/categories")
    public Result&lt;List&lt;String&gt;&gt; getAllCategories() {
        try {
            return Result.success(projectService.getAllCategories());
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/latest")
    public Result&lt;List&lt;DonationProject&gt;&gt; getLatestActiveProjects(
            @RequestParam(defaultValue = "6") int limit) {
        try {
            return Result.success(projectService.getLatestActiveProjects(limit));
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/my")
    public Result&lt;Page&lt;DonationProject&gt;&gt; getMyProjects(
            @RequestHeader(value = "userId", defaultValue = "1") Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            return Result.success(projectService.getMyProjects(userId, page, size));
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PutMapping("/{id}/status")
    public Result&lt;?&gt; updateProjectStatus(@PathVariable Long id,
                                          @RequestParam String status) {
        try {
            DonationProject.ProjectStatus projectStatus = DonationProject.ProjectStatus.valueOf(status);
            return Result.success(projectService.updateProjectStatus(id, projectStatus));
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public Result&lt;?&gt; deleteProject(@PathVariable Long id,
                                    @RequestHeader(value = "userId", defaultValue = "1") Long userId) {
        try {
            projectService.deleteProject(id, userId);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
