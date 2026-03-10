package com.eldercare.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eldercare.common.Result;
import com.eldercare.entity.CheckResult;
import com.eldercare.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/result")
public class ResultController {

    @Autowired
    private ResultService resultService;

    @GetMapping("/list")
    public Result<Page<CheckResult>> list(@RequestParam(defaultValue = "1") int pageNum,
                                          @RequestParam(defaultValue = "10") int pageSize,
                                          @RequestParam(required = false) Long appointmentId,
                                          @RequestParam(required = false) Long elderId,
                                          @RequestParam(required = false) Integer abnormalFlag) {
        return Result.success(resultService.page(pageNum, pageSize, appointmentId, elderId, abnormalFlag));
    }

    @PostMapping("/add")
    public Result<String> add(@RequestBody CheckResult checkResult,
                              @RequestAttribute("userId") String userId) {
        resultService.add(checkResult, Long.valueOf(userId));
        return Result.success();
    }

    @PutMapping("/update")
    public Result<String> update(@RequestBody CheckResult checkResult,
                                 @RequestAttribute("userId") String userId) {
        resultService.update(checkResult, Long.valueOf(userId));
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id) {
        resultService.delete(id);
        return Result.success();
    }
}
