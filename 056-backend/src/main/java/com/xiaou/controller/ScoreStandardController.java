package com.xiaou.controller;

import com.xiaou.common.Result;
import com.xiaou.entity.ScoreStandard;
import com.xiaou.service.ScoreStandardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/score-standard")
public class ScoreStandardController {

    @Autowired
    private ScoreStandardService scoreStandardService;

    @GetMapping("/list/{competitionId}")
    public Result<?> list(@PathVariable Long competitionId) {
        return Result.success(scoreStandardService.getByCompetitionId(competitionId));
    }

    @PostMapping("/save")
    public Result<?> save(@RequestBody Map<String, Object> params) {
        Long competitionId = Long.valueOf(params.get("competitionId").toString());
        List<ScoreStandard> standards = (List<ScoreStandard>) params.get("standards");
        scoreStandardService.save(competitionId, standards);
        return Result.success();
    }
}
