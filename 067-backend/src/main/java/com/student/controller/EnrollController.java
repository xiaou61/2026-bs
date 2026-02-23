package com.student.controller;

import com.student.common.BusinessException;
import com.student.common.Result;
import com.student.service.EnrollService;
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
import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/enroll")
public class EnrollController {

    @Resource
    private EnrollService enrollService;

    @GetMapping("/page")
    public Result<?> page(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) Long courseId,
                          @RequestParam(required = false) Long studentId,
                          @RequestParam(required = false) Integer status,
                          HttpServletRequest request) {
        String role = (String) request.getAttribute("role");
        Long userId = (Long) request.getAttribute("userId");
        checkStaff(role);
        return Result.success(enrollService.page(pageNum, pageSize, courseId, studentId, status, role, userId));
    }

    @GetMapping("/my-page")
    public Result<?> myPage(@RequestParam(defaultValue = "1") Integer pageNum,
                            @RequestParam(defaultValue = "10") Integer pageSize,
                            @RequestParam(required = false) Integer status,
                            @RequestParam(required = false) Long courseId,
                            HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(enrollService.myPage(userId, pageNum, pageSize, status, courseId));
    }

    @PostMapping
    public Result<?> add(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        String role = (String) request.getAttribute("role");
        if (!"STUDENT".equals(role)) {
            throw new BusinessException("仅学生可选课");
        }
        if (params.get("courseId") == null) {
            throw new BusinessException("课程不能为空");
        }
        Long userId = (Long) request.getAttribute("userId");
        enrollService.add(((Number) params.get("courseId")).longValue(), userId);
        return Result.success();
    }

    @PutMapping("/status")
    public Result<?> updateStatus(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        String role = (String) request.getAttribute("role");
        checkStaff(role);
        if (params.get("id") == null || params.get("status") == null) {
            throw new BusinessException("参数不完整");
        }
        Long userId = (Long) request.getAttribute("userId");
        BigDecimal score = params.get("score") == null ? null : new BigDecimal(params.get("score").toString());
        String remark = params.get("remark") == null ? "" : params.get("remark").toString();
        enrollService.updateStatus(((Number) params.get("id")).longValue(), ((Number) params.get("status")).intValue(), score, remark, role, userId);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, HttpServletRequest request) {
        String role = (String) request.getAttribute("role");
        Long userId = (Long) request.getAttribute("userId");
        enrollService.deleteById(id, role, userId);
        return Result.success();
    }

    private void checkStaff(String role) {
        if (!"ADMIN".equals(role) && !"TEACHER".equals(role)) {
            throw new BusinessException("无权限操作");
        }
    }
}
