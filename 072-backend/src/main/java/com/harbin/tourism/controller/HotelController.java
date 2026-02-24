package com.harbin.tourism.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.harbin.tourism.common.Result;
import com.harbin.tourism.entity.Hotel;
import com.harbin.tourism.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hotels")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @GetMapping("/list")
    public Result<Page<Hotel>> list(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String type) {
        return Result.success(hotelService.page(pageNum, pageSize, keyword, type));
    }

    @GetMapping("/{id}")
    public Result<Hotel> getById(@PathVariable Long id) {
        return Result.success(hotelService.getById(id));
    }

    @GetMapping("/all")
    public Result<List<Hotel>> all() {
        return Result.success(hotelService.listAll());
    }

    @PostMapping
    public Result<Void> add(@RequestBody Hotel hotel) {
        hotelService.save(hotel);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestBody Hotel hotel) {
        hotelService.update(hotel);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        hotelService.delete(id);
        return Result.success();
    }
}
