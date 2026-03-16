package com.teachres.controller;

import com.github.pagehelper.PageInfo;
import com.teachres.common.Result;
import com.teachres.entity.EvalIndicator;
import com.teachres.service.IndicatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/indicator")
public class IndicatorController {

    @Autowired
    private IndicatorService indicatorService;

    @GetMapping("/list")
    public Result<PageInfo<EvalIndicator>> list(@RequestParam(defaultValue = "1") Integer pageNum,
                                                @RequestParam(defaultValue = "10") Integer pageSize,
                                                @RequestParam(required = false) String indicatorName,
                                                @RequestParam(required = false) Integer status) {
        return Result.success(indicatorService.list(indicatorName, status, pageNum, pageSize));
    }

    @GetMapping("/enabled")
    public Result<List<EvalIndicator>> enabled() {
        return Result.success(indicatorService.enabledList());
    }

    @PostMapping("/add")
    public Result<String> add(@RequestBody EvalIndicator indicator) {
        indicatorService.add(indicator);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<String> update(@RequestBody EvalIndicator indicator) {
        indicatorService.update(indicator);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id) {
        indicatorService.delete(id);
        return Result.success();
    }
}
