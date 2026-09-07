package com.xiaou.dreamdonation.controller;

import com.xiaou.dreamdonation.common.Result;
import com.xiaou.dreamdonation.dto.ProjectCreateDTO;
import com.xiaou.dreamdonation.entity.DonationProject;
import com.xiaou.dreamdonation.entity.User;
import com.xiaou.dreamdonation.service.DonationProjectService;
import com.xiaou.dreamdonation.service.UserService;
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
    private final UserService userService;

    @PostMapping
    public Result<?> createProject(@Valid @RequestBody ProjectCreateDTO dto,
                                    @RequestHeader(value = "Authorization", required = false) String token) {
        User user = userService.getAuthenticatedUser(token);
        return Result.success(projectService.createProject(dto, user.getId()));
    }

    @GetMapping
    public Result<Page<DonationProject>> listProjects(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String status) {
        return Result.success(projectService.listProjects(page, size, category, status));
    }

    @GetMapping("/{id}")
    public Result<?> getProjectById(@PathVariable Long id) {
        return Result.success(projectService.getProjectById(id));
    }

    @GetMapping("/categories")
    public Result<List<String>> getAllCategories() {
        return Result.success(projectService.getAllCategories());
    }

    @GetMapping("/latest")
    public Result<List<DonationProject>> getLatestActiveProjects(
            @RequestParam(defaultValue = "6") int limit) {
        return Result.success(projectService.getLatestActiveProjects(limit));
    }

    @GetMapping("/my")
    public Result<Page<DonationProject>> getMyProjects(
            @RequestHeader(value = "Authorization", required = false) String token,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        User user = userService.getAuthenticatedUser(token);
        return Result.success(projectService.getMyProjects(user.getId(), page, size));
    }

    @PutMapping("/{id}/status")
    public Result<?> updateProjectStatus(@PathVariable Long id,
                                          @RequestParam String status,
                                          @RequestHeader(value = "Authorization", required = false) String token) {
        User user = userService.getAuthenticatedUser(token);
        DonationProject.ProjectStatus projectStatus = DonationProject.ProjectStatus.valueOf(status);
        return Result.success(projectService.updateProjectStatus(id, projectStatus, user.getId()));
    }

    @DeleteMapping("/{id}")
    public Result<?> deleteProject(@PathVariable Long id,
                                    @RequestHeader(value = "Authorization", required = false) String token) {
        User user = userService.getAuthenticatedUser(token);
        projectService.deleteProject(id, user.getId());
        return Result.success();
    }
}
