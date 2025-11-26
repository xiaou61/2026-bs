package com.xiaou.community.controller;

import com.xiaou.community.common.Result;
import com.xiaou.community.entity.Repair;
import com.xiaou.community.service.RepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/repair")
public class RepairController {
    @Autowired
    private RepairService repairService;

    @PostMapping("/submit")
    public Result<String> submit(@RequestBody Repair repair) {
        repairService.submit(repair);
        return Result.success("Submitted successfully");
    }

    @PostMapping("/status/{id}")
    public Result<String> updateStatus(@PathVariable Integer id, @RequestParam String status) {
        repairService.updateStatus(id, status);
        return Result.success("Status updated successfully");
    }

    @GetMapping("/owner/{ownerId}")
    public Result<List<Repair>> getByOwnerId(@PathVariable Integer ownerId) {
        return Result.success(repairService.getByOwnerId(ownerId));
    }

    @GetMapping("/list")
    public Result<List<Repair>> list() {
        return Result.success(repairService.getAll());
    }
}
