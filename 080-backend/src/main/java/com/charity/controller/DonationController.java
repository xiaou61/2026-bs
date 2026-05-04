package com.charity.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.charity.common.Result;
import com.charity.entity.Donation;
import com.charity.service.DonationService;
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
                                          @RequestParam(required = false) Long childId,
                                          @RequestAttribute("userId") String userId) {
        Page<Donation> page = donationService.getList(pageNum, pageSize, donorId, childId, Long.parseLong(userId));
        return Result.success(page);
    }

    @PostMapping("/add")
    public Result<String> add(@RequestBody Donation donation,
                              @RequestAttribute("userId") String userId) {
        donationService.add(donation, Long.parseLong(userId));
        return Result.success();
    }

    @PutMapping("/confirm/{id}")
    public Result<String> confirm(@PathVariable Long id,
                                  @RequestAttribute("userId") String userId) {
        donationService.confirm(id, Long.parseLong(userId));
        return Result.success();
    }
}
