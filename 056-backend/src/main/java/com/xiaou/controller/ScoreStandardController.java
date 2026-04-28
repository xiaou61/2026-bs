package com.xiaou.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/list/{competitionId}")
    public Result<?> list(@PathVariable Long competitionId) {
        return Result.success(scoreStandardService.getByCompetitionId(competitionId));
    }

    @PostMapping("/save")
    public Result<?> save(@RequestBody Map<String, Object> params) {
        Long competitionId = Long.valueOf(params.get("competitionId").toString());
        List<?> standardList = (List<?>) params.get("standards");
        List<ScoreStandard> standards = standardList.stream()
                .map(item -> objectMapper.convertValue(item, ScoreStandard.class))
                .toList();
        scoreStandardService.save(competitionId, standards);
        return Result.success();
    }
}
