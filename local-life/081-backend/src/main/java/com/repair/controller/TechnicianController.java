package com.repair.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.repair.common.Result;
import com.repair.entity.Technician;
import com.repair.service.TechnicianService;
import com.repair.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/technician")
public class TechnicianController {

    @Autowired
    private TechnicianService technicianService;

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public Result<Page<Technician>> getList(@RequestParam(defaultValue = "1") int pageNum,
                                            @RequestParam(defaultValue = "10") int pageSize,
                                            @RequestParam(required = false) String name,
                                            @RequestParam(required = false) String level,
                                            @RequestParam(required = false) Integer workStatus,
                                            @RequestAttribute("userId") String userId) {
        userService.requireAdmin(Long.parseLong(userId));
        return Result.success(technicianService.getList(pageNum, pageSize, name, level, workStatus));
    }

    @PostMapping("/add")
    public Result<String> add(@RequestBody Technician technician, @RequestAttribute("userId") String userId) {
        userService.requireAdmin(Long.parseLong(userId));
        technicianService.add(technician);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<String> update(@RequestBody Technician technician, @RequestAttribute("userId") String userId) {
        userService.requireAdmin(Long.parseLong(userId));
        technicianService.update(technician);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id, @RequestAttribute("userId") String userId) {
        userService.requireAdmin(Long.parseLong(userId));
        technicianService.delete(id);
        return Result.success();
    }
}
