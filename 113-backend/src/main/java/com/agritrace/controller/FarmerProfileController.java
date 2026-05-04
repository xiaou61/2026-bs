package com.agritrace.controller;

import com.agritrace.common.Result;
import com.agritrace.entity.FarmerProfile;
import com.agritrace.service.FarmerProfileService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/farmer")
@RequiredArgsConstructor
public class FarmerProfileController {
    private final FarmerProfileService service;

    @GetMapping("/page")
    public Result<IPage<FarmerProfile>> page(@RequestParam(required = false) Integer pageNum,
                                            @RequestParam(required = false) Integer pageSize,
                                            @RequestParam(required = false) String keyword,
                                            @RequestParam(required = false) String status) {
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestBody FarmerProfile entity) {
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestBody FarmerProfile entity) {
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return Result.success();
    }

    @PutMapping("/activate/{id}")
    public Result<Void> activate(@PathVariable Long id) {
        service.updateStatus(id, "ACTIVE");
        return Result.success();
    }

    @PutMapping("/freeze/{id}")
    public Result<Void> freeze(@PathVariable Long id) {
        service.updateStatus(id, "FROZEN");
        return Result.success();
    }

}
