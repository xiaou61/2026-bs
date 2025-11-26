package com.xiaou.community.controller;

import com.xiaou.community.common.Result;
import com.xiaou.community.entity.Visitor;
import com.xiaou.community.service.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/visitor")
public class VisitorController {
    @Autowired
    private VisitorService visitorService;

    @PostMapping("/register")
    public Result<String> register(@RequestBody Visitor visitor) {
        visitorService.register(visitor);
        return Result.success("Registered successfully");
    }

    @PostMapping("/status/{id}")
    public Result<String> updateStatus(@PathVariable Integer id, @RequestParam String status) {
        visitorService.updateStatus(id, status);
        return Result.success("Status updated successfully");
    }

    @GetMapping("/owner/{ownerId}")
    public Result<List<Visitor>> getByOwnerId(@PathVariable Integer ownerId) {
        return Result.success(visitorService.getByOwnerId(ownerId));
    }

    @GetMapping("/list")
    public Result<List<Visitor>> list() {
        return Result.success(visitorService.getAll());
    }
}
