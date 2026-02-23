package com.property.controller;

import com.property.common.BusinessException;
import com.property.common.Result;
import com.property.entity.Announcement;
import com.property.service.AnnouncementService;
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
        checkAdmin((String) request.getAttribute("role"));
        return Result.success(announcementService.page(pageNum, pageSize, title, status));
    }

    @PostMapping
    public Result<?> add(@RequestBody Announcement announcement, HttpServletRequest request) {
        checkAdmin((String) request.getAttribute("role"));
        announcementService.save(announcement, (Long) request.getAttribute("userId"));
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody Announcement announcement, HttpServletRequest request) {
        checkAdmin((String) request.getAttribute("role"));
        announcementService.save(announcement, (Long) request.getAttribute("userId"));
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, HttpServletRequest request) {
        checkAdmin((String) request.getAttribute("role"));
        announcementService.deleteById(id);
        return Result.success();
    }

    private void checkAdmin(String role) {
        if (!"ADMIN".equals(role)) {
            throw new BusinessException("无权限操作");
        }
    }
}
