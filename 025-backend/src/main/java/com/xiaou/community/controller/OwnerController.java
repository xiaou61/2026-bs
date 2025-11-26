package com.xiaou.community.controller;

import com.xiaou.community.common.Result;
import com.xiaou.community.entity.Owner;
import com.xiaou.community.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/owner")
public class OwnerController {
    @Autowired
    private OwnerService ownerService;

    @PostMapping("/add")
    public Result<String> add(@RequestBody Owner owner) {
        ownerService.add(owner);
        return Result.success("Added successfully");
    }

    @PostMapping("/update")
    public Result<String> update(@RequestBody Owner owner) {
        ownerService.update(owner);
        return Result.success("Updated successfully");
    }

    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Integer id) {
        ownerService.delete(id);
        return Result.success("Deleted successfully");
    }

    @GetMapping("/{id}")
    public Result<Owner> getById(@PathVariable Integer id) {
        return Result.success(ownerService.getById(id));
    }

    @GetMapping("/user/{userId}")
    public Result<Owner> getByUserId(@PathVariable Integer userId) {
        return Result.success(ownerService.getByUserId(userId));
    }

    @GetMapping("/list")
    public Result<List<Owner>> list() {
        return Result.success(ownerService.getAll());
    }
}
