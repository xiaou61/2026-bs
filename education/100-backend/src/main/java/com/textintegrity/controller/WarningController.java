package com.textintegrity.controller;

import com.github.pagehelper.PageInfo;
import com.textintegrity.common.Result;
import com.textintegrity.service.AccessLogService;
import com.textintegrity.service.AuthService;
import com.textintegrity.service.CrudService;
import com.textintegrity.service.IntegrityService;
import com.textintegrity.utils.Filters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/warning")
public class WarningController {

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
                                                      String keyword,
                                                      Long studentId,
                                                      String warningLevel,
                                                      Integer status) {
        return Result.success(crudService.page("warning", pageNum, pageSize, keyword, Filters.of("studentId", studentId, "warningLevel", warningLevel, "status", status)));
    }

    @PutMapping("/handle")
    public Result<Void> handle(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody Map<String, Object> data) {
        authService.assertReviewer(role);
        integrityService.handleWarning(userId, data);
        accessLogService.record(userId, "诚信预警", "处理", "处理预警：" + data.get("id"));
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertReviewer(role);
        crudService.delete("warning", id);
        accessLogService.record(userId, "诚信预警", "删除", "删除预警：" + id);
        return Result.success();
    }
}
