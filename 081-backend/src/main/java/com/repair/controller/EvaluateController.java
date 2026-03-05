package com.repair.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.repair.common.Result;
import com.repair.entity.Evaluate;
import com.repair.service.EvaluateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/evaluate")
public class EvaluateController {

    @Autowired
    private EvaluateService evaluateService;

    @GetMapping("/list")
    public Result<Page<Evaluate>> getList(@RequestParam(defaultValue = "1") int pageNum,
                                          @RequestParam(defaultValue = "10") int pageSize,
                                          @RequestParam(required = false) Long orderId,
                                          @RequestParam(required = false) Long technicianId,
                                          @RequestParam(required = false) Integer score) {
        return Result.success(evaluateService.getList(pageNum, pageSize, orderId, technicianId, score));
    }

    @PostMapping("/add")
    public Result<String> add(@RequestBody Evaluate evaluate) {
        evaluateService.add(evaluate);
        return Result.success();
    }

    @PostMapping("/user/submit")
    public Result<String> userSubmit(@RequestBody Evaluate evaluate,
                                     @RequestAttribute("userId") String userId) {
        evaluateService.userSubmit(evaluate, Long.parseLong(userId));
        return Result.success();
    }

    @GetMapping("/user/my")
    public Result<Page<Evaluate>> getMy(@RequestParam(defaultValue = "1") int pageNum,
                                        @RequestParam(defaultValue = "10") int pageSize,
                                        @RequestAttribute("userId") String userId) {
        return Result.success(evaluateService.getMyList(pageNum, pageSize, Long.parseLong(userId)));
    }
}
