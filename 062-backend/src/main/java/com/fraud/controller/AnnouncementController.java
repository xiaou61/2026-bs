package com.fraud.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fraud.common.BusinessException;
import com.fraud.common.Result;
import com.fraud.entity.Announcement;
import com.fraud.service.AnnouncementService;
import com.fraud.service.OperationLogService;
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

@RestController
@RequestMapping("/api/announcement")
public class AnnouncementController {

    @Resource
    private AnnouncementService announcementService;

    @Resource
    private OperationLogService operationLogService;

    @GetMapping("/list")
    public Result<?> list() {
        return Result.success(announcementService.list());
    }

    @GetMapping("/page")
    public Result<?> page(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) String title,
                          @RequestParam(required = false) Integer status,
                          HttpServletRequest request) {
        checkRiskRole((String) request.getAttribute("role"));
        Page<Announcement> page = announcementService.page(pageNum, pageSize, title, status);
        return Result.success(page);
    }

    @PostMapping
    public Result<?> add(@RequestBody Announcement announcement, HttpServletRequest request) {
        checkRiskRole((String) request.getAttribute("role"));
        Long userId = (Long) request.getAttribute("userId");
        announcementService.save(announcement, userId);
        operationLogService.add("ANNOUNCEMENT", "ADD", userId, (String) request.getAttribute("role"), announcement.getTitle(), "新增公告");
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody Announcement announcement, HttpServletRequest request) {
        checkRiskRole((String) request.getAttribute("role"));
        Long userId = (Long) request.getAttribute("userId");
        announcementService.save(announcement, userId);
        operationLogService.add("ANNOUNCEMENT", "UPDATE", userId, (String) request.getAttribute("role"), String.valueOf(announcement.getId()), "更新公告");
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, HttpServletRequest request) {
        checkRiskRole((String) request.getAttribute("role"));
        announcementService.deleteById(id);
        operationLogService.add("ANNOUNCEMENT", "DELETE", (Long) request.getAttribute("userId"), (String) request.getAttribute("role"), String.valueOf(id), "删除公告");
        return Result.success();
    }

    private void checkRiskRole(String role) {
        if (!"ADMIN".equals(role) && !"ANALYST".equals(role)) {
            throw new BusinessException("无权限操作");
        }
    }
}
