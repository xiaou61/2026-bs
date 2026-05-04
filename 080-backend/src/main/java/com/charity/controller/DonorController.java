package com.charity.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.charity.common.Result;
import com.charity.entity.Donor;
import com.charity.service.DonorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/donor")
public class DonorController {

    @Autowired
    private DonorService donorService;

    @GetMapping("/list")
    public Result<Page<Donor>> getList(@RequestParam(defaultValue = "1") int pageNum,
                                       @RequestParam(defaultValue = "10") int pageSize,
                                       @RequestParam(required = false) String donorType,
                                       @RequestAttribute("userId") String userId) {
        Page<Donor> page = donorService.getList(pageNum, pageSize, donorType, Long.parseLong(userId));
        return Result.success(page);
    }

    @PostMapping("/add")
    public Result<String> add(@RequestBody Donor donor,
                              @RequestAttribute("userId") String userId) {
        donorService.add(donor, Long.parseLong(userId));
        return Result.success();
    }

    @PutMapping("/update")
    public Result<String> update(@RequestBody Donor donor,
                                 @RequestAttribute("userId") String userId) {
        donorService.update(donor, Long.parseLong(userId));
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id,
                                 @RequestAttribute("userId") String userId) {
        donorService.delete(id, Long.parseLong(userId));
        return Result.success();
    }
}
