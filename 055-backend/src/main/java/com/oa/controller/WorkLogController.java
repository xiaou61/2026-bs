package com.oa.controller;

import com.oa.common.Result;
import com.oa.entity.WorkLog;
import com.oa.service.WorkLogService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/workLog")
@RequiredArgsConstructor
public class WorkLogController {
    private final WorkLogService workLogService;

    @GetMapping("/list")
    public Result list(@RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       String date, String keyword) {
        return Result.success(workLogService.getList(pageNum, pageSize, date, keyword));
    }

    @GetMapping("/my")
    public Result my(HttpServletRequest request,
                     @RequestParam(defaultValue = "1") Integer pageNum,
                     @RequestParam(defaultValue = "10") Integer pageSize,
                     String date) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(workLogService.getMyList(userId, pageNum, pageSize, date));
    }

    @PostMapping
    public Result add(HttpServletRequest request, @RequestBody WorkLog workLog) {
        Long userId = (Long) request.getAttribute("userId");
        workLogService.add(userId, workLog);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody WorkLog workLog) {
        workLogService.update(workLog);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        workLogService.delete(id);
        return Result.success();
    }
}
