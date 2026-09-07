package com.xiaou.dreamdonation.controller;

import com.xiaou.dreamdonation.common.Result;
import com.xiaou.dreamdonation.dto.ProgressCreateDTO;
import com.xiaou.dreamdonation.entity.ProjectProgress;
import com.xiaou.dreamdonation.entity.User;
import com.xiaou.dreamdonation.service.ProjectProgressService;
import com.xiaou.dreamdonation.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/progress")
@RequiredArgsConstructor
@CrossOrigin
public class ProjectProgressController {
    private final ProjectProgressService progressService;
    private final UserService userService;

    @PostMapping
    public Result<?> createProgress(@Valid @RequestBody ProgressCreateDTO dto,
                                     @RequestHeader(value = "Authorization", required = false) String token) {
        User user = userService.getAuthenticatedUser(token);
        return Result.success(progressService.createProgress(dto, user.getId()));
    }

    @GetMapping("/project/{projectId}")
    public Result<List<ProjectProgress>> getProjectProgress(@PathVariable Long projectId) {
        return Result.success(progressService.getProjectProgress(projectId));
    }

    @DeleteMapping("/{id}")
    public Result<?> deleteProgress(@PathVariable Long id,
                                     @RequestHeader(value = "Authorization", required = false) String token) {
        User user = userService.getAuthenticatedUser(token);
        progressService.deleteProgress(id, user.getId());
        return Result.success();
    }
}
