package com.xiaou.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaou.common.Result;
import com.xiaou.entity.Score;
import com.xiaou.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/score")
public class ScoreController {

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/pending")
    public Result<?> pending(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             Long competitionId,
                             HttpServletRequest request) {
        Long judgeId = (Long) request.getAttribute("userId");
        return Result.success(scoreService.getPendingWorks(judgeId, pageNum, pageSize, competitionId));
    }

    @PostMapping("/submit")
    public Result<?> submit(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Long judgeId = (Long) request.getAttribute("userId");
        Long workId = Long.valueOf(params.get("workId").toString());
        List<?> scoreList = (List<?>) params.get("scores");
        List<Score> scores = scoreList.stream()
                .map(obj -> objectMapper.convertValue(obj, Score.class))
                .toList();
        String comment = (String) params.get("comment");
        scoreService.submitScore(judgeId, workId, scores, comment);
        return Result.success();
    }

    @GetMapping("/detail/{workId}")
    public Result<?> detail(@PathVariable Long workId) {
        return Result.success(scoreService.getScoreDetail(workId));
    }

    @GetMapping("/my/{workId}")
    public Result<?> myScore(@PathVariable Long workId, HttpServletRequest request) {
        Long judgeId = (Long) request.getAttribute("userId");
        return Result.success(scoreService.getMyScore(judgeId, workId));
    }
}
