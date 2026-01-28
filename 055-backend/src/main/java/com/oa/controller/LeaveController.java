package com.oa.controller;

import com.oa.common.Result;
import com.oa.entity.LeaveRequest;
import com.oa.service.LeaveService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/leave")
@RequiredArgsConstructor
public class LeaveController {
    private final LeaveService leaveService;

    @GetMapping("/list")
    public Result list(@RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       Integer status, String keyword) {
        return Result.success(leaveService.getList(pageNum, pageSize, status, keyword));
    }

    @GetMapping("/my")
    public Result my(HttpServletRequest request,
                     @RequestParam(defaultValue = "1") Integer pageNum,
                     @RequestParam(defaultValue = "10") Integer pageSize) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(leaveService.getMyList(userId, pageNum, pageSize));
    }

    @PostMapping
    public Result add(HttpServletRequest request, @RequestBody LeaveRequest leave) {
        Long userId = (Long) request.getAttribute("userId");
        leaveService.add(userId, leave);
        return Result.success();
    }

    @PutMapping("/approve/{id}")
    public Result approve(HttpServletRequest request, @PathVariable Long id, @RequestBody Map<String, Object> params) {
        Long userId = (Long) request.getAttribute("userId");
        Integer status = (Integer) params.get("status");
        String remark = (String) params.get("remark");
        leaveService.approve(id, userId, status, remark);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        leaveService.delete(id);
        return Result.success();
    }

    @GetMapping("/pendingCount")
    public Result pendingCount() {
        return Result.success(leaveService.getPendingCount());
    }
}
