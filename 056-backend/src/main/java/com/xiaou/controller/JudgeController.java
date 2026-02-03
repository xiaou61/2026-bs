package com.xiaou.controller;

import com.xiaou.common.Result;
import com.xiaou.service.JudgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/judge")
public class JudgeController {

    @Autowired
    private JudgeService judgeService;

    @GetMapping("/list/{competitionId}")
    public Result<?> list(@PathVariable Long competitionId) {
        return Result.success(judgeService.getByCompetitionId(competitionId));
    }

    @PostMapping("/assign")
    public Result<?> assign(@RequestBody Map<String, Object> params) {
        Long competitionId = Long.valueOf(params.get("competitionId").toString());
        List<Long> judgeIds = ((List<?>) params.get("judgeIds")).stream()
                .map(id -> Long.valueOf(id.toString()))
                .toList();
        judgeService.assign(competitionId, judgeIds);
        return Result.success();
    }
}
