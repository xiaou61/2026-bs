package com.xiaou.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaou.common.Result;
import com.xiaou.entity.LeaveRequest;
import com.xiaou.entity.MakeupRequest;
import com.xiaou.service.LeaveRequestService;
import com.xiaou.service.MakeupRequestService;
import com.xiaou.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/leave")
public class LeaveController {

    @Autowired
    private LeaveRequestService leaveRequestService;

    @Autowired
    private MakeupRequestService makeupRequestService;

    @Autowired
    private JwtUtil jwtUtil;

    // ============ 请假申请相关 ============

    @PostMapping("/submit")
    public Result<?> submitLeave(@RequestHeader("Authorization") String token,
                                 @RequestBody LeaveRequest request) {
        Long userId = jwtUtil.getUserIdFromToken(token.replace("Bearer ", ""));
        request.setStudentId(userId);
        LeaveRequest result = leaveRequestService.submitLeave(request);
        return Result.success(result);
    }

    @PutMapping("/approve/{id}")
    public Result<?> approveLeave(@RequestHeader("Authorization") String token,
                                  @PathVariable Long id,
                                  @RequestBody Map<String, Object> params) {
        Long userId = jwtUtil.getUserIdFromToken(token.replace("Bearer ", ""));
        Integer status = (Integer) params.get("status");
        String rejectReason = (String) params.get("rejectReason");
        
        try {
            leaveRequestService.approveLeave(id, userId, status, rejectReason);
            return Result.success("审批成功");
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/student")
    public Result<?> pageStudentLeaves(@RequestHeader("Authorization") String token,
                                       @RequestParam(defaultValue = "1") Integer page,
                                       @RequestParam(defaultValue = "10") Integer size) {
        Long userId = jwtUtil.getUserIdFromToken(token.replace("Bearer ", ""));
        IPage<LeaveRequest> result = leaveRequestService.pageStudentLeaves(page, size, userId);
        return Result.success(result);
    }

    @GetMapping("/teacher")
    public Result<?> pageTeacherLeaves(@RequestHeader("Authorization") String token,
                                       @RequestParam(defaultValue = "1") Integer page,
                                       @RequestParam(defaultValue = "10") Integer size,
                                       @RequestParam(required = false) Integer status) {
        Long userId = jwtUtil.getUserIdFromToken(token.replace("Bearer ", ""));
        IPage<LeaveRequest> result = leaveRequestService.pageTeacherLeaves(page, size, userId, status);
        return Result.success(result);
    }

    @GetMapping("/{id}")
    public Result<?> getLeaveById(@PathVariable Long id) {
        return Result.success(leaveRequestService.getById(id));
    }

    // ============ 补签申请相关 ============

    @PostMapping("/makeup/submit")
    public Result<?> submitMakeup(@RequestHeader("Authorization") String token,
                                  @RequestBody MakeupRequest request) {
        Long userId = jwtUtil.getUserIdFromToken(token.replace("Bearer ", ""));
        request.setStudentId(userId);
        MakeupRequest result = makeupRequestService.submitMakeup(request);
        return Result.success(result);
    }

    @PutMapping("/makeup/approve/{id}")
    public Result<?> approveMakeup(@RequestHeader("Authorization") String token,
                                   @PathVariable Long id,
                                   @RequestBody Map<String, Object> params) {
        Long userId = jwtUtil.getUserIdFromToken(token.replace("Bearer ", ""));
        Integer status = (Integer) params.get("status");
        String rejectReason = (String) params.get("rejectReason");
        
        try {
            makeupRequestService.approveMakeup(id, userId, status, rejectReason);
            return Result.success("审批成功");
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/makeup/student")
    public Result<?> pageStudentMakeups(@RequestHeader("Authorization") String token,
                                        @RequestParam(defaultValue = "1") Integer page,
                                        @RequestParam(defaultValue = "10") Integer size) {
        Long userId = jwtUtil.getUserIdFromToken(token.replace("Bearer ", ""));
        IPage<MakeupRequest> result = makeupRequestService.pageStudentMakeups(page, size, userId);
        return Result.success(result);
    }

    @GetMapping("/makeup/teacher")
    public Result<?> pageTeacherMakeups(@RequestHeader("Authorization") String token,
                                        @RequestParam(defaultValue = "1") Integer page,
                                        @RequestParam(defaultValue = "10") Integer size,
                                        @RequestParam(required = false) Integer status) {
        Long userId = jwtUtil.getUserIdFromToken(token.replace("Bearer ", ""));
        IPage<MakeupRequest> result = makeupRequestService.pageTeacherMakeups(page, size, userId, status);
        return Result.success(result);
    }

    @GetMapping("/makeup/{id}")
    public Result<?> getMakeupById(@PathVariable Long id) {
        return Result.success(makeupRequestService.getById(id));
    }
}
