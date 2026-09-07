package com.xiaou.controller;

import com.xiaou.common.Result;
import com.xiaou.service.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stats")
public class StatsController {

    @Autowired
    private StatsService statsService;

    @GetMapping("/dashboard")
    public Result<?> dashboard() {
        return Result.success(statsService.getDashboard());
    }

    @GetMapping("/competition/{id}")
    public Result<?> competition(@PathVariable Long id) {
        return Result.success(statsService.getCompetitionStats(id));
    }

    @GetMapping("/score-progress/{competitionId}")
    public Result<?> scoreProgress(@PathVariable Long competitionId) {
        return Result.success(statsService.getScoreProgress(competitionId));
    }
}
