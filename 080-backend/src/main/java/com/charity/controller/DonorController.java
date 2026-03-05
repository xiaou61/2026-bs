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
                                       @RequestParam(required = false) String donorType) {
        Page<Donor> page = donorService.getList(pageNum, pageSize, donorType);
        return Result.success(page);
    }

    @PostMapping("/add")
    public Result<String> add(@RequestBody Donor donor) {
        donorService.add(donor);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<String> update(@RequestBody Donor donor) {
        donorService.update(donor);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id) {
        donorService.delete(id);
        return Result.success();
    }
}
