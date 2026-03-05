package com.charity.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.charity.common.Result;
import com.charity.entity.Child;
import com.charity.service.ChildService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/child")
public class ChildController {

    @Autowired
    private ChildService childService;

    @GetMapping("/list")
    public Result<Page<Child>> getList(@RequestParam(defaultValue = "1") int pageNum,
                                       @RequestParam(defaultValue = "10") int pageSize,
                                       @RequestParam(required = false) String name,
                                       @RequestParam(required = false) String province,
                                       @RequestParam(required = false) String city,
                                       @RequestParam(required = false) Integer sponsorStatus) {
        Page<Child> page = childService.getList(pageNum, pageSize, name, province, city, sponsorStatus);
        return Result.success(page);
    }

    @GetMapping("/detail/{id}")
    public Result<Child> getDetail(@PathVariable Long id) {
        Child child = childService.getDetail(id);
        return Result.success(child);
    }

    @PostMapping("/add")
    public Result<String> add(@RequestBody Child child) {
        childService.add(child);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<String> update(@RequestBody Child child) {
        childService.update(child);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id) {
        childService.delete(id);
        return Result.success();
    }
}
