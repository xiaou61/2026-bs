package com.teacher.controller;

import com.teacher.common.BusinessException;
import com.teacher.common.Result;
import com.teacher.entity.UserComplaint;
import com.teacher.service.ComplaintService;
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
@RequestMapping("/api/complaint")
public class ComplaintController {

    @Resource
    private ComplaintService complaintService;

    @GetMapping("/page")
    public Result<?> page(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) String status,
                          @RequestParam(required = false) String username,
                          HttpServletRequest request) {
        checkAdmin((String) request.getAttribute("role"));
        return Result.success(complaintService.page(pageNum, pageSize, status, username));
    }

    @GetMapping("/my-page")
    public Result<?> myPage(@RequestParam(defaultValue = "1") Integer pageNum,
                            @RequestParam(defaultValue = "10") Integer pageSize,
                            @RequestParam(required = false) String status,
                            HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(complaintService.myPage(userId, pageNum, pageSize, status));
    }

    @PostMapping
    public Result<?> add(@RequestBody UserComplaint complaint, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(complaintService.save(userId, complaint));
    }

    @PutMapping("/handle")
    public Result<?> handle(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        checkAdmin((String) request.getAttribute("role"));
        if (params.get("id") == null || params.get("handleResult") == null) {
            throw new BusinessException("参数不完整");
        }
        Long userId = (Long) request.getAttribute("userId");
        complaintService.handle(((Number) params.get("id")).longValue(), params.get("handleResult").toString(), userId);
        return Result.success();
    }

    private void checkAdmin(String role) {
        if (!"ADMIN".equals(role)) {
            throw new BusinessException("无权限操作");
        }
    }
}


