package com.xiaou.recruitment.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaou.recruitment.common.Result;
import com.xiaou.recruitment.entity.Experience;
import com.xiaou.recruitment.service.ExperienceService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/experience")
public class ExperienceController {

    @Autowired
    private ExperienceService experienceService;

    @PostMapping("/publish")
    public Result<?> publishExperience(@RequestBody Experience experience, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        experience.setUserId(userId);
        if (experienceService.publishExperience(experience)) {
            return Result.success(experience);
        }
        return Result.error("发布失败");
    }

    @GetMapping("/list")
    public Result<?> getExperienceList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String keyword) {
        IPage<Experience> experiencePage = experienceService.getExperienceList(page, size, type, keyword);
        return Result.success(experiencePage);
    }

    @GetMapping("/{id}")
    public Result<?> getExperienceById(@PathVariable Long id) {
        Experience experience = experienceService.getExperienceById(id);
        if (experience != null) {
            return Result.success(experience);
        }
        return Result.error("经验不存在");
    }

    @PostMapping("/like/{id}")
    public Result<?> likeExperience(@PathVariable Long id) {
        if (experienceService.likeExperience(id)) {
            return Result.success();
        }
        return Result.error("点赞失败");
    }
}
