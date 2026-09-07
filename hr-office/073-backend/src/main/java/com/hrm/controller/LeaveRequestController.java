package com.hrm.controller;

import com.hrm.common.BusinessException;
import com.hrm.common.Result;
import com.hrm.entity.LeaveRequest;
import com.hrm.entity.User;
import com.hrm.service.LeaveRequestService;
import com.hrm.service.UserService;
import com.hrm.utils.AuthUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/leave")
public class LeaveRequestController {
    @Resource
    private LeaveRequestService leaveRequestService;
    @Resource
    private UserService userService;

    @GetMapping("/{id}")
    public Result getById(@PathVariable Long id, HttpServletRequest request) {
        LeaveRequest leaveRequest = leaveRequestService.getById(id);
        requireOwnLeaveOrHr(request, leaveRequest);
        return Result.success(leaveRequest);
    }

    @GetMapping("/list")
    public Result getList(@RequestParam(required = false) Long employeeId,
                          @RequestParam(required = false) String employeeName,
                          @RequestParam(required = false) String type,
                          @RequestParam(required = false) String status,
                          @RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          HttpServletRequest request) {
        if (!AuthUtils.isAdminOrHr(request)) {
            employeeId = currentEmployeeId(request);
            employeeName = null;
        }
        return Result.success(leaveRequestService.getList(employeeId, employeeName, type, status, pageNum, pageSize));
    }

    @GetMapping("/pendingCount")
    public Result getPendingCount(HttpServletRequest request) {
        AuthUtils.requireAdminOrHr(request);
        return Result.success(leaveRequestService.getPendingCount());
    }

    @PostMapping
    public Result add(@RequestBody LeaveRequest leaveRequest, HttpServletRequest request) {
        if (!AuthUtils.isAdminOrHr(request)) {
            leaveRequest.setEmployeeId(currentEmployeeId(request));
        }
        leaveRequestService.add(leaveRequest);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody LeaveRequest leaveRequest, HttpServletRequest request) {
        LeaveRequest existing = leaveRequestService.getById(leaveRequest.getId());
        requireOwnLeaveOrHr(request, existing);
        if (!AuthUtils.isAdminOrHr(request)) {
            leaveRequest.setStatus(null);
            leaveRequest.setApproverId(null);
            leaveRequest.setApproveTime(null);
        }
        leaveRequestService.update(leaveRequest);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id, HttpServletRequest request) {
        LeaveRequest existing = leaveRequestService.getById(id);
        requireOwnLeaveOrHr(request, existing);
        leaveRequestService.delete(id);
        return Result.success();
    }

    @PostMapping("/approve")
    public Result approve(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        AuthUtils.requireAdminOrHr(request);
        Long id = Long.valueOf(params.get("id").toString());
        String status = params.get("status").toString();
        Long userId = AuthUtils.currentUserId(request);
        leaveRequestService.approve(id, userId, status);
        return Result.success();
    }

    private Long currentEmployeeId(HttpServletRequest request) {
        User user = userService.getById(AuthUtils.currentUserId(request));
        if (user == null || user.getEmployeeId() == null) {
            throw new BusinessException(403, "当前账号未绑定员工档案");
        }
        return user.getEmployeeId();
    }

    private void requireOwnLeaveOrHr(HttpServletRequest request, LeaveRequest leaveRequest) {
        if (leaveRequest == null) {
            throw new BusinessException(404, "请假记录不存在");
        }
        if (!AuthUtils.isAdminOrHr(request) && !leaveRequest.getEmployeeId().equals(currentEmployeeId(request))) {
            throw new BusinessException(403, "只能访问自己的请假记录");
        }
    }
}
