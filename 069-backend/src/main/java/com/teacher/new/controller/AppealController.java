package com.teacher.new.controller;

import com.teacher.new.common.BusinessException;
import com.teacher.new.common.Result;
import com.teacher.new.entity.EvaluationAppeal;
import com.teacher.new.service.AppealService;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping("/api/appeal")
public class AppealController {

    @Resource
    private AppealService appealService;

    @GetMapping("/page")
    public Result<?> page(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) String status,
                          @RequestParam(required = false) Long teacherId,
                          HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        String role = (String) request.getAttribute("role");
        return Result.success(appealService.page(pageNum, pageSize, status, teacherId, userId, role));
    }

    @GetMapping("/my-page")
    public Result<?> myPage(@RequestParam(defaultValue = "1") Integer pageNum,
                            @RequestParam(defaultValue = "10") Integer pageSize,
                            @RequestParam(required = false) String status,
                            HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        String role = (String) request.getAttribute("role");
        return Result.success(appealService.page(pageNum, pageSize, status, null, userId, role));
    }

    @PostMapping
    public Result<?> create(@RequestBody EvaluationAppeal appeal, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        String role = (String) request.getAttribute("role");
        return Result.success(appealService.create(userId, role, appeal));
    }

    @PutMapping("/handle")
    public Result<?> handle(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        if (!"ADMIN".equals(request.getAttribute("role"))) {
            throw new BusinessException("无权限操作");
        }
        if (params.get("id") == null || params.get("status") == null) {
            throw new BusinessException("参数不完整");
        }
        appealService.handle(
                ((Number) params.get("id")).longValue(),
                String.valueOf(params.get("status")),
                params.get("replyText") == null ? "" : String.valueOf(params.get("replyText")),
                (Long) request.getAttribute("userId")
        );
        return Result.success();
    }
}
