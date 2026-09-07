package com.xiaou.campusshare.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.campusshare.common.Result;
import com.xiaou.campusshare.entity.SkillService;
import com.xiaou.campusshare.service.SkillServiceService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/skill")
public class SkillServiceController {

    @Autowired
    private SkillServiceService skillServiceService;

    @PostMapping("/publish")
    public Result<?> publish(HttpServletRequest request, @RequestBody SkillService service) {
        Long userId = (Long) request.getAttribute("userId");
        service.setUserId(userId);
        service.setStatus(1);
        service.setViewCount(0);
        service.setOrderCount(0);
        service.setIsDeleted(0);
        skillServiceService.save(service);
        return Result.success("发布成功");
    }

    @GetMapping("/list")
    public Result<Page<SkillService>> getList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String keyword) {
        Page<SkillService> result = skillServiceService.getServiceList(page, size, category, keyword);
        return Result.success(result);
    }

    @GetMapping("/{id}")
    public Result<SkillService> getDetail(@PathVariable Long id) {
        SkillService service = skillServiceService.getById(id);
        if (service != null) {
            skillServiceService.increaseViewCount(id);
        }
        return Result.success(service);
    }

    @PutMapping("/{id}")
    public Result<?> update(HttpServletRequest request, @PathVariable Long id, @RequestBody SkillService service) {
        Long userId = (Long) request.getAttribute("userId");
        SkillService existing = skillServiceService.getById(id);
        if (existing == null || !existing.getUserId().equals(userId)) {
            return Result.error("无权限");
        }
        service.setId(id);
        service.setUserId(userId);
        skillServiceService.updateById(service);
        return Result.success("更新成功");
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(HttpServletRequest request, @PathVariable Long id) {
        Long userId = (Long) request.getAttribute("userId");
        SkillService service = skillServiceService.getById(id);
        if (service == null || !service.getUserId().equals(userId)) {
            return Result.error("无权限");
        }
        service.setIsDeleted(1);
        skillServiceService.updateById(service);
        return Result.success("删除成功");
    }

    @GetMapping("/my")
    public Result<Page<SkillService>> getMy(
            HttpServletRequest request,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        Long userId = (Long) request.getAttribute("userId");
        LambdaQueryWrapper<SkillService> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SkillService::getUserId, userId);
        wrapper.eq(SkillService::getIsDeleted, 0);
        wrapper.orderByDesc(SkillService::getCreateTime);
        Page<SkillService> result = skillServiceService.page(new Page<>(page, size), wrapper);
        return Result.success(result);
    }

    @GetMapping("/search")
    public Result<Page<SkillService>> search(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam String keyword) {
        Page<SkillService> result = skillServiceService.getServiceList(page, size, null, keyword);
        return Result.success(result);
    }
}

