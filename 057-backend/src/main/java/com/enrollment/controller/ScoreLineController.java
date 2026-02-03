package com.enrollment.controller;

import com.enrollment.common.Result;
import com.enrollment.entity.ScoreLine;
import com.enrollment.service.ScoreLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/scoreline")
public class ScoreLineController {

    @Autowired
    private ScoreLineService scoreLineService;

    @GetMapping("/page")
    public Result<?> getPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             @RequestParam(required = false) Integer year,
                             @RequestParam(required = false) String province) {
        return Result.success(scoreLineService.getPage(pageNum, pageSize, year, province));
    }

    @PostMapping
    public Result<?> add(@RequestBody ScoreLine scoreLine) {
        scoreLineService.add(scoreLine);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody ScoreLine scoreLine) {
        scoreLineService.update(scoreLine);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        scoreLineService.delete(id);
        return Result.success();
    }
}
