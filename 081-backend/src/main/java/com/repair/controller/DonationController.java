package com.repair.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.repair.common.Result;
import com.repair.entity.Donation;
import com.repair.service.DonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/donation")
public class DonationController {

    @Autowired
    private DonationService donationService;

    @GetMapping("/list")
    public Result<Page<Donation>> getList(@RequestParam(defaultValue = "1") int pageNum,
                                          @RequestParam(defaultValue = "10") int pageSize,
                                          @RequestParam(required = false) Long donorId,
                                          @RequestParam(required = false) Long childId) {
        Page<Donation> page = donationService.getList(pageNum, pageSize, donorId, childId);
        return Result.success(page);
    }

    @PostMapping("/add")
    public Result<String> add(@RequestBody Donation donation) {
        donationService.add(donation);
        return Result.success();
    }

    @PutMapping("/confirm/{id}")
    public Result<String> confirm(@PathVariable Long id) {
        donationService.confirm(id);
        return Result.success();
    }
}

