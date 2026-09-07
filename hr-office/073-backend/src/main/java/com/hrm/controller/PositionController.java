package com.hrm.controller;

import com.hrm.common.Result;
import com.hrm.entity.Position;
import com.hrm.service.PositionService;
import com.hrm.utils.AuthUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/position")
public class PositionController {
    @Resource
    private PositionService positionService;

    @GetMapping("/{id}")
    public Result getById(@PathVariable Long id) {
        return Result.success(positionService.getById(id));
    }

    @GetMapping("/list")
    public Result getList(@RequestParam(required = false) String name,
                          @RequestParam(required = false) Integer status,
                          @RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(positionService.getList(name, status, pageNum, pageSize));
    }

    @GetMapping("/all")
    public Result getAll() {
        return Result.success(positionService.getAll());
    }

    @PostMapping
    public Result add(@RequestBody Position position, HttpServletRequest request) {
        AuthUtils.requireAdminOrHr(request);
        positionService.add(position);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody Position position, HttpServletRequest request) {
        AuthUtils.requireAdminOrHr(request);
        positionService.update(position);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id, HttpServletRequest request) {
        AuthUtils.requireAdminOrHr(request);
        positionService.delete(id);
        return Result.success();
    }
}
