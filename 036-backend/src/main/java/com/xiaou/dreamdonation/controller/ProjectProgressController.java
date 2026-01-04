package com.xiaou.dreamdonation.controller;

import com.xiaou.dreamdonation.common.Result;
import com.xiaou.dreamdonation.dto.ProgressCreateDTO;
import com.xiaou.dreamdonation.entity.ProjectProgress;
import com.xiaou.dreamdonation.service.ProjectProgressService;
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

    @PostMapping
    public Result&lt;?&gt; createProgress(@Valid @RequestBody ProgressCreateDTO dto,
                                     @RequestHeader(value = "userId", defaultValue = "1") Long userId) {
        try {
            return Result.success(progressService.createProgress(dto, userId));
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/project/{projectId}")
    public Result&lt;List&lt;ProjectProgress&gt;&gt; getProjectProgress(@PathVariable Long projectId) {
        try {
            return Result.success(progressService.getProjectProgress(projectId));
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public Result&lt;?&gt; deleteProgress(@PathVariable Long id,
                                     @RequestHeader(value = "userId", defaultValue = "1") Long userId) {
        try {
            progressService.deleteProgress(id, userId);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
