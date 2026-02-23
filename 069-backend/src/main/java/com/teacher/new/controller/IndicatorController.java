package com.teacher.new.controller;

import com.teacher.new.common.BusinessException;
import com.teacher.new.common.Result;
import com.teacher.new.entity.EvaluationIndicator;
import com.teacher.new.service.IndicatorService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/indicator")
public class IndicatorController {

    @Resource
    private IndicatorService indicatorService;

    @GetMapping("/list")
    public Result<?> list() {
        return Result.success(indicatorService.list());
    }

    @GetMapping("/page")
    public Result<?> page(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) String indicatorName,
                          @RequestParam(required = false) String dimensionName,
                          @RequestParam(required = false) Integer status,
                          HttpServletRequest request) {
        checkAdmin((String) request.getAttribute("role"));
        return Result.success(indicatorService.page(pageNum, pageSize, indicatorName, dimensionName, status));
    }

    @PostMapping
    public Result<?> add(@RequestBody EvaluationIndicator indicator, HttpServletRequest request) {
        checkAdmin((String) request.getAttribute("role"));
        indicatorService.save(indicator);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody EvaluationIndicator indicator, HttpServletRequest request) {
        checkAdmin((String) request.getAttribute("role"));
        indicatorService.save(indicator);
        return Result.success();
    }

    @PutMapping("/status")
    public Result<?> updateStatus(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        checkAdmin((String) request.getAttribute("role"));
        if (params.get("id") == null || params.get("status") == null) {
            throw new BusinessException("参数不完整");
        }
        indicatorService.updateStatus(((Number) params.get("id")).longValue(), ((Number) params.get("status")).intValue());
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, HttpServletRequest request) {
        checkAdmin((String) request.getAttribute("role"));
        indicatorService.deleteById(id);
        return Result.success();
    }

    private void checkAdmin(String role) {
        if (!"ADMIN".equals(role)) {
            throw new BusinessException("无权限操作");
        }
    }
}
