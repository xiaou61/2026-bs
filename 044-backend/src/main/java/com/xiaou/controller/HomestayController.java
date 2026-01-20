package com.xiaou.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaou.common.Result;
import com.xiaou.entity.Facility;
import com.xiaou.entity.Homestay;
import com.xiaou.entity.RoomType;
import com.xiaou.mapper.FacilityMapper;
import com.xiaou.service.HomestayService;
import com.xiaou.service.ReviewService;
import com.xiaou.service.RoomTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "民宿管理")
@RestController
@RequestMapping("/api/homestay")
@RequiredArgsConstructor
public class HomestayController {

    private final HomestayService homestayService;
    private final RoomTypeService roomTypeService;
    private final ReviewService reviewService;
    private final FacilityMapper facilityMapper;

    @Operation(summary = "民宿列表")
    @GetMapping("/list")
    public Result<IPage<Homestay>> list(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) String keyword) {
        return Result.success(homestayService.pageList(current, size, city, minPrice, maxPrice, keyword));
    }

    @Operation(summary = "民宿详情")
    @GetMapping("/detail/{id}")
    public Result<Map<String, Object>> detail(@PathVariable Long id) {
        Homestay homestay = homestayService.detail(id);
        if (homestay == null) {
            return Result.error("民宿不存在");
        }
        List<RoomType> roomTypes = roomTypeService.listByHomestay(id);
        List<Facility> facilities = facilityMapper.selectByHomestayId(id);
        var reviews = reviewService.pageByHomestay(id, 1, 5);

        Map<String, Object> result = new HashMap<>();
        result.put("homestay", homestay);
        result.put("roomTypes", roomTypes);
        result.put("facilities", facilities);
        result.put("reviews", reviews);
        return Result.success(result);
    }

    @Operation(summary = "搜索民宿")
    @GetMapping("/search")
    public Result<IPage<Homestay>> search(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size) {
        return Result.success(homestayService.pageList(current, size, null, null, null, keyword));
    }
}
