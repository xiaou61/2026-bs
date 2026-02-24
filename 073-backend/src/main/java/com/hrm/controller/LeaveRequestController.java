package com.hrm.controller;

import com.hrm.common.Result;
import com.hrm.entity.LeaveRequest;
import com.hrm.service.LeaveRequestService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/leave")
public class LeaveRequestController {
    @Resource
    private LeaveRequestService leaveRequestService;

    @GetMapping("/{id}")
    public Result getById(@PathVariable Long id) {
        return Result.success(leaveRequestService.getById(id));
    }

    @GetMapping("/list")
    public Result getList(@RequestParam(required = false) Long employeeId,
                          @RequestParam(required = false) String employeeName,
                          @RequestParam(required = false) String type,
                          @RequestParam(required = false) String status,
                          @RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(leaveRequestService.getList(employeeId, employeeName, type, status, pageNum, pageSize));
    }

    @GetMapping("/pendingCount")
    public Result getPendingCount() {
        return Result.success(leaveRequestService.getPendingCount());
    }

    @PostMapping
    public Result add(@RequestBody LeaveRequest leaveRequest) {
        leaveRequestService.add(leaveRequest);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody LeaveRequest leaveRequest) {
        leaveRequestService.update(leaveRequest);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        leaveRequestService.delete(id);
        return Result.success();
    }

    @PostMapping("/approve")
    public Result approve(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Long id = Long.valueOf(params.get("id").toString());
        String status = params.get("status").toString();
        Long userId = (Long) request.getAttribute("userId");
        leaveRequestService.approve(id, userId, status);
        return Result.success();
    }
}
