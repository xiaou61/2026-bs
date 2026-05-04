package com.repair.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.repair.common.Result;
import com.repair.entity.SparePart;
import com.repair.service.SparePartService;
import com.repair.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/part")
public class SparePartController {

    @Autowired
    private SparePartService sparePartService;

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public Result<Page<SparePart>> getList(@RequestParam(defaultValue = "1") int pageNum,
                                           @RequestParam(defaultValue = "10") int pageSize,
                                           @RequestParam(required = false) String name,
                                           @RequestParam(required = false) String brand,
                                           @RequestParam(required = false) Integer status,
                                           @RequestAttribute("userId") String userId) {
        userService.requireAdmin(Long.parseLong(userId));
        return Result.success(sparePartService.getList(pageNum, pageSize, name, brand, status));
    }

    @PostMapping("/add")
    public Result<String> add(@RequestBody SparePart part, @RequestAttribute("userId") String userId) {
        userService.requireAdmin(Long.parseLong(userId));
        sparePartService.add(part);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<String> update(@RequestBody SparePart part, @RequestAttribute("userId") String userId) {
        userService.requireAdmin(Long.parseLong(userId));
        sparePartService.update(part);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id, @RequestAttribute("userId") String userId) {
        userService.requireAdmin(Long.parseLong(userId));
        sparePartService.delete(id);
        return Result.success();
    }
}
