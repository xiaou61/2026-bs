package com.xiaou.controller;

import com.xiaou.common.Result;
import com.xiaou.entity.Competition;
import com.xiaou.service.CompetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/competition")
public class CompetitionController {

    @Autowired
    private CompetitionService competitionService;

    @GetMapping("/list")
    public Result<?> list(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          String keyword, Long categoryId, Integer status) {
        return Result.success(competitionService.getList(pageNum, pageSize, keyword, categoryId, status));
    }

    @GetMapping("/public/list")
    public Result<?> publicList(Long categoryId) {
        return Result.success(competitionService.getPublicList(categoryId));
    }

    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Long id) {
        return Result.success(competitionService.getById(id));
    }

    @GetMapping("/public/{id}")
    public Result<?> getPublicById(@PathVariable Long id) {
        return Result.success(competitionService.getById(id));
    }

    @PostMapping("/save")
    public Result<?> save(@RequestBody Competition competition, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        if (competition.getId() == null) {
            competition.setCreateUser(userId);
        }
        competitionService.save(competition);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        competitionService.delete(id);
        return Result.success();
    }

    @PutMapping("/status/{id}")
    public Result<?> updateStatus(@PathVariable Long id, @RequestParam Integer status) {
        competitionService.updateStatus(id, status);
        return Result.success();
    }

    @PutMapping("/publish/{id}")
    public Result<?> publishResult(@PathVariable Long id) {
        competitionService.publishResult(id);
        return Result.success();
    }
}
