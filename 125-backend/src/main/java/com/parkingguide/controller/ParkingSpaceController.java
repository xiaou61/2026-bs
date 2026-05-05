package com.parkingguide.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.parkingguide.common.Result;
import com.parkingguide.entity.ParkingSpace;
import com.parkingguide.service.ParkingSpaceService;
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
@RequestMapping("/api/space")
@RequiredArgsConstructor
public class ParkingSpaceController {
    private final ParkingSpaceService service;

    @GetMapping("/page")
    public Result<IPage<ParkingSpace>> page(@RequestParam(required = false) Integer pageNum,
                                    @RequestParam(required = false) Integer pageSize,
                                    @RequestParam(required = false) String keyword,
                                    @RequestParam(required = false) String status) {
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestBody ParkingSpace entity) {
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestBody ParkingSpace entity) {
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return Result.success();
    }

    @PutMapping("/available/{id}")
    public Result<Void> available(@PathVariable Long id) {
        service.updateStatus(id, "AVAILABLE");
        return Result.success();
    }

    @PutMapping("/occupy/{id}")
    public Result<Void> occupy(@PathVariable Long id) {
        service.updateStatus(id, "OCCUPIED");
        return Result.success();
    }

}
