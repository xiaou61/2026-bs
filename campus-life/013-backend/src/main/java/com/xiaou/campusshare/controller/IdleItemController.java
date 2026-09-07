package com.xiaou.campusshare.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.campusshare.common.Result;
import com.xiaou.campusshare.entity.IdleItem;
import com.xiaou.campusshare.service.IdleItemService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/idle")
public class IdleItemController {

    @Autowired
    private IdleItemService idleItemService;

    @PostMapping("/publish")
    public Result<?> publish(HttpServletRequest request, @RequestBody IdleItem item) {
        Long userId = (Long) request.getAttribute("userId");
        item.setUserId(userId);
        item.setStatus(0);
        item.setViewCount(0);
        item.setOrderCount(0);
        item.setIsDeleted(0);
        idleItemService.save(item);
        return Result.success("发布成功，等待审核");
    }

    @GetMapping("/list")
    public Result<Page<IdleItem>> getList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String keyword) {
        Page<IdleItem> result = idleItemService.getItemList(page, size, category, keyword);
        return Result.success(result);
    }

    @GetMapping("/{id}")
    public Result<IdleItem> getDetail(@PathVariable Long id) {
        IdleItem item = idleItemService.getById(id);
        if (item != null) {
            idleItemService.increaseViewCount(id);
        }
        return Result.success(item);
    }

    @PutMapping("/{id}")
    public Result<?> update(HttpServletRequest request, @PathVariable Long id, @RequestBody IdleItem item) {
        Long userId = (Long) request.getAttribute("userId");
        IdleItem existing = idleItemService.getById(id);
        if (existing == null || !existing.getUserId().equals(userId)) {
            return Result.error("无权限");
        }
        item.setId(id);
        item.setUserId(userId);
        item.setStatus(0);
        idleItemService.updateById(item);
        return Result.success("更新成功，等待审核");
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(HttpServletRequest request, @PathVariable Long id) {
        Long userId = (Long) request.getAttribute("userId");
        IdleItem item = idleItemService.getById(id);
        if (item == null || !item.getUserId().equals(userId)) {
            return Result.error("无权限");
        }
        item.setIsDeleted(1);
        idleItemService.updateById(item);
        return Result.success("删除成功");
    }

    @GetMapping("/my")
    public Result<Page<IdleItem>> getMy(
            HttpServletRequest request,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        Long userId = (Long) request.getAttribute("userId");
        LambdaQueryWrapper<IdleItem> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(IdleItem::getUserId, userId);
        wrapper.eq(IdleItem::getIsDeleted, 0);
        wrapper.orderByDesc(IdleItem::getCreateTime);
        Page<IdleItem> result = idleItemService.page(new Page<>(page, size), wrapper);
        return Result.success(result);
    }

    @GetMapping("/search")
    public Result<Page<IdleItem>> search(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam String keyword) {
        Page<IdleItem> result = idleItemService.getItemList(page, size, null, keyword);
        return Result.success(result);
    }
}

