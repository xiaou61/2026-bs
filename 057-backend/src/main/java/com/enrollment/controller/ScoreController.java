package com.enrollment.controller;

import com.enrollment.common.Result;
import com.enrollment.entity.Score;
import com.enrollment.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/score")
public class ScoreController {

    @Autowired
    private ScoreService scoreService;

    @GetMapping("/page")
    public Result<?> getPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             @RequestParam(required = false) String studentName,
                             @RequestParam(required = false) Integer year) {
        return Result.success(scoreService.getPage(pageNum, pageSize, studentName, year));
    }

    @PostMapping
    public Result<?> add(@RequestBody Score score) {
        scoreService.add(score);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody Score score) {
        scoreService.update(score);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        scoreService.delete(id);
        return Result.success();
    }

    @PostMapping("/import")
    public Result<?> batchImport(@RequestBody List<Score> scores) {
        scoreService.batchImport(scores);
        return Result.success();
    }
}
