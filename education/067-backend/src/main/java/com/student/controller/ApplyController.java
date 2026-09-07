package com.student.controller;

import com.student.common.BusinessException;
import com.student.common.Result;
import com.student.service.ApplyService;
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
@RequestMapping("/api/apply")
public class ApplyController {

    @Resource
    private ApplyService applyService;

    @GetMapping("/page")
    public Result<?> page(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) Long jobId,
                          @RequestParam(required = false) Long studentId,
                          @RequestParam(required = false) Integer status,
                          HttpServletRequest request) {
        String role = (String) request.getAttribute("role");
        Long userId = (Long) request.getAttribute("userId");
        checkStaff(role);
        return Result.success(applyService.page(pageNum, pageSize, jobId, studentId, status, role, userId));
    }

    @GetMapping("/my-page")
    public Result<?> myPage(@RequestParam(defaultValue = "1") Integer pageNum,
                            @RequestParam(defaultValue = "10") Integer pageSize,
                            @RequestParam(required = false) Integer status,
                            @RequestParam(required = false) Long jobId,
                            HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(applyService.myPage(userId, pageNum, pageSize, status, jobId));
    }

    @PostMapping
    public Result<?> add(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        String role = (String) request.getAttribute("role");
        if (!"STUDENT".equals(role)) {
            throw new BusinessException("仅学生可投递岗位");
        }
        if (params.get("jobId") == null) {
            throw new BusinessException("岗位不能为空");
        }
        Long userId = (Long) request.getAttribute("userId");
        String resumeUrl = params.get("resumeUrl") == null ? "" : params.get("resumeUrl").toString();
        String applyNote = params.get("applyNote") == null ? "" : params.get("applyNote").toString();
        applyService.add(((Number) params.get("jobId")).longValue(), userId, resumeUrl, applyNote);
        return Result.success();
    }

    @PutMapping("/review")
    public Result<?> review(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        String role = (String) request.getAttribute("role");
        checkStaff(role);
        if (params.get("id") == null || params.get("status") == null) {
            throw new BusinessException("参数不完整");
        }
        Long userId = (Long) request.getAttribute("userId");
        String reviewNote = params.get("reviewNote") == null ? "" : params.get("reviewNote").toString();
        applyService.review(((Number) params.get("id")).longValue(), ((Number) params.get("status")).intValue(), reviewNote, userId, role, userId);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, HttpServletRequest request) {
        String role = (String) request.getAttribute("role");
        Long userId = (Long) request.getAttribute("userId");
        applyService.deleteById(id, role, userId);
        return Result.success();
    }

    private void checkStaff(String role) {
        if (!"ADMIN".equals(role) && !"TEACHER".equals(role)) {
            throw new BusinessException("无权限操作");
        }
    }
}
