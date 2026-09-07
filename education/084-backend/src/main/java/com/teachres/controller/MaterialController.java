package com.teachres.controller;

import com.github.pagehelper.PageInfo;
import com.teachres.common.Result;
import com.teachres.entity.MaterialInfo;
import com.teachres.service.MaterialService;
import com.teachres.utils.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/material")
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    @GetMapping("/list")
    public Result<PageInfo<MaterialInfo>> list(@RequestParam(defaultValue = "1") Integer pageNum,
                                               @RequestParam(defaultValue = "10") Integer pageSize,
                                               @RequestParam(required = false) String title,
                                               @RequestParam(required = false) Long categoryId,
                                               @RequestParam(required = false) Integer auditStatus,
                                               @RequestParam(required = false) Integer publishStatus,
                                               @RequestParam(required = false) Long uploaderId,
                                               HttpServletRequest request) {
        AuthUtils.requireAnyRole(request, "admin", "teacher");
        if (!AuthUtils.isAdmin(request)) {
            uploaderId = AuthUtils.getUserId(request);
        }
        return Result.success(materialService.list(title, categoryId, auditStatus, publishStatus, uploaderId, pageNum, pageSize));
    }

    @GetMapping("/public/list")
    public Result<PageInfo<MaterialInfo>> publicList(@RequestParam(defaultValue = "1") Integer pageNum,
                                                     @RequestParam(defaultValue = "10") Integer pageSize,
                                                     @RequestParam(required = false) String title,
                                                     @RequestParam(required = false) Long categoryId) {
        return Result.success(materialService.publicList(title, categoryId, pageNum, pageSize));
    }

    @GetMapping("/detail/{id}")
    public Result<Map<String, Object>> detail(@PathVariable Long id, HttpServletRequest request) {
        return Result.success(materialService.detail(id));
    }

    @PostMapping("/add")
    public Result<String> add(@RequestBody Map<String, Object> params,
                              @RequestAttribute("userId") Long userId,
                              HttpServletRequest request) {
        AuthUtils.requireAnyRole(request, "admin", "teacher");
        materialService.add(params, userId);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<String> update(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        AuthUtils.requireAnyRole(request, "admin", "teacher");
        materialService.update(params, request);
        return Result.success();
    }

    @PutMapping("/publish")
    public Result<String> publish(@RequestParam Long id, @RequestParam Integer publishStatus, HttpServletRequest request) {
        AuthUtils.requireAnyRole(request, "admin", "teacher");
        materialService.publish(id, publishStatus, request);
        return Result.success();
    }

    @PostMapping("/download/{id}")
    public Result<String> download(@PathVariable Long id,
                                   @RequestAttribute("userId") Long userId) {
        materialService.download(id, userId);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id, HttpServletRequest request) {
        AuthUtils.requireAnyRole(request, "admin", "teacher");
        materialService.delete(id, request);
        return Result.success();
    }
}
