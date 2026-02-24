package com.craft.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.craft.common.BusinessException;
import com.craft.common.Result;
import com.craft.entity.CraftItem;
import com.craft.service.CraftItemService;
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
import java.util.Map;

@RestController
@RequestMapping("/api/item")
public class ItemController {

    @Resource
    private CraftItemService itemService;

    @GetMapping("/list")
    public Result<?> list(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) String title,
                          @RequestParam(required = false) Long categoryId,
                          @RequestParam(required = false) String craftName) {
        Page<CraftItem> page = itemService.list(pageNum, pageSize, title, categoryId, craftName);
        return Result.success(page);
    }

    @GetMapping("/page")
    public Result<?> page(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) String title,
                          @RequestParam(required = false) Long categoryId,
                          @RequestParam(required = false) String craftName,
                          @RequestParam(required = false) Integer status,
                          HttpServletRequest request) {
        String role = (String) request.getAttribute("role");
        Long userId = (Long) request.getAttribute("userId");
        Page<CraftItem> page;
        if ("ADMIN".equals(role)) {
            page = itemService.page(pageNum, pageSize, title, categoryId, craftName, status, null);
        } else {
            page = itemService.page(pageNum, pageSize, title, categoryId, craftName, status, userId);
        }
        return Result.success(page);
    }

    @GetMapping("/{id}")
    public Result<?> detail(@PathVariable Long id) {
        return Result.success(itemService.getById(id));
    }

    @PostMapping
    public Result<?> add(@RequestBody CraftItem item, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        String role = (String) request.getAttribute("role");
        itemService.save(item, userId, role);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody CraftItem item, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        String role = (String) request.getAttribute("role");
        itemService.save(item, userId, role);
        return Result.success();
    }

    @PutMapping("/status")
    public Result<?> updateStatus(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        checkAdmin((String) request.getAttribute("role"));
        if (params.get("id") == null || params.get("status") == null) {
            throw new BusinessException("参数不完整");
        }
        Long id = ((Number) params.get("id")).longValue();
        Integer status = ((Number) params.get("status")).intValue();
        itemService.updateStatus(id, status);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        String role = (String) request.getAttribute("role");
        itemService.deleteById(id, userId, role);
        return Result.success();
    }

    private void checkAdmin(String role) {
        if (!"ADMIN".equals(role)) {
            throw new BusinessException("无权限操作");
        }
    }
}

