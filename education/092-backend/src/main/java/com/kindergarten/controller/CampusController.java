package com.kindergarten.controller;

import com.github.pagehelper.PageInfo;
import com.kindergarten.common.Result;
import com.kindergarten.entity.CampusInfo;
import com.kindergarten.service.CampusService;
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

import java.util.List;

@RestController
@RequestMapping("/api/campus")
public class CampusController {

    @Autowired
    private CampusService departmentService;

    @GetMapping("/list")
    public Result<PageInfo<CampusInfo>> list(@RequestParam(defaultValue = "1") Integer pageNum,
                                                 @RequestParam(defaultValue = "10") Integer pageSize,
                                                 @RequestParam(required = false) String name,
                                                 @RequestParam(required = false) Integer status) {
        return Result.success(departmentService.list(name, status, pageNum, pageSize));
    }

    @GetMapping("/enabled")
    public Result<List<CampusInfo>> enabled() {
        return Result.success(departmentService.enabledList());
    }

    @PostMapping("/add")
    public Result<String> add(@RequestBody CampusInfo entity, @RequestAttribute("role") String role) {
        departmentService.add(entity, role);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<String> update(@RequestBody CampusInfo entity, @RequestAttribute("role") String role) {
        departmentService.update(entity, role);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id, @RequestAttribute("role") String role) {
        departmentService.delete(id, role);
        return Result.success();
    }
}
