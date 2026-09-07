package com.textintegrity.controller;

import com.github.pagehelper.PageInfo;
import com.textintegrity.common.Result;
import com.textintegrity.service.AccessLogService;
import com.textintegrity.service.AuthService;
import com.textintegrity.service.CrudService;
import com.textintegrity.service.IntegrityService;
import com.textintegrity.utils.Filters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/result")
public class ResultController {

    @Autowired
    private CrudService crudService;

    @Autowired
    private IntegrityService integrityService;

    @Autowired
    private AuthService authService;

    @Autowired
    private AccessLogService accessLogService;

    @GetMapping("/page")
    public Result<PageInfo<Map<String, Object>>> page(@RequestParam(defaultValue = "1") Integer pageNum,
                                                      @RequestParam(defaultValue = "10") Integer pageSize,
                                                      Long taskId,
                                                      String riskLevel,
                                                      Integer reviewStatus) {
        return Result.success(crudService.page("result", pageNum, pageSize, null, Filters.of("taskId", taskId, "riskLevel", riskLevel, "reviewStatus", reviewStatus)));
    }

    @PutMapping("/review")
    public Result<Void> review(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody Map<String, Object> data) {
        authService.assertReviewer(role);
        integrityService.reviewResult(data);
        accessLogService.record(userId, "检测结果", "复核", "复核检测结果：" + data.get("id"));
        return Result.success();
    }
}
