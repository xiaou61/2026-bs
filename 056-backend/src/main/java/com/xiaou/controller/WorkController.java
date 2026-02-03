package com.xiaou.controller;

import com.xiaou.common.Result;
import com.xiaou.entity.Work;
import com.xiaou.service.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/work")
public class WorkController {

    @Autowired
    private WorkService workService;

    @GetMapping("/list")
    public Result<?> list(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          Long competitionId, Integer status, String keyword) {
        return Result.success(workService.getList(pageNum, pageSize, competitionId, status, keyword));
    }

    @GetMapping("/my")
    public Result<?> myWorks(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(workService.getMyWorks(userId, pageNum, pageSize));
    }

    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Long id) {
        return Result.success(workService.getById(id));
    }

    @PostMapping("/submit")
    public Result<?> submit(@RequestBody Work work, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        workService.submit(work, userId);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<?> update(@RequestBody Work work, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        workService.update(work, userId);
        return Result.success();
    }

    @PutMapping("/withdraw/{id}")
    public Result<?> withdraw(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        workService.withdraw(id, userId);
        return Result.success();
    }

    @PutMapping("/audit")
    public Result<?> audit(@RequestBody Map<String, Object> params) {
        Long id = Long.valueOf(params.get("id").toString());
        Integer status = (Integer) params.get("status");
        String rejectReason = (String) params.get("rejectReason");
        workService.audit(id, status, rejectReason);
        return Result.success();
    }
}
