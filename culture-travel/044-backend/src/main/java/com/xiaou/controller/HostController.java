package com.xiaou.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaou.common.BusinessException;
import com.xiaou.common.Result;
import com.xiaou.entity.Homestay;
import com.xiaou.entity.Booking;
import com.xiaou.entity.RoomType;
import com.xiaou.service.BookingService;
import com.xiaou.service.HomestayService;
import com.xiaou.service.ReviewService;
import com.xiaou.service.RoomTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Tag(name = "房东管理")
@RestController
@RequestMapping("/api/host")
@RequiredArgsConstructor
public class HostController {

    private final HomestayService homestayService;
    private final RoomTypeService roomTypeService;
    private final BookingService bookingService;
    private final ReviewService reviewService;

    @Operation(summary = "我的民宿列表")
    @GetMapping("/homestay/list")
    public Result<IPage<Homestay>> listHomestay(@AuthenticationPrincipal UserDetails userDetails,
                                                @RequestParam(defaultValue = "1") Integer current,
                                                @RequestParam(defaultValue = "10") Integer size) {
        Long hostId = Long.parseLong(userDetails.getUsername());
        return Result.success(homestayService.pageByHost(hostId, current, size));
    }

    @Operation(summary = "添加民宿")
    @PostMapping("/homestay/add")
    public Result<Void> addHomestay(@AuthenticationPrincipal UserDetails userDetails,
                                    @RequestBody Homestay homestay) {
        Long hostId = Long.parseLong(userDetails.getUsername());
        homestay.setHostId(hostId);
        homestay.setStatus(2); // 审核中
        homestayService.save(homestay);
        return Result.success();
    }

    @Operation(summary = "更新民宿")
    @PutMapping("/homestay/update")
    public Result<Void> updateHomestay(@AuthenticationPrincipal UserDetails userDetails,
                                       @RequestBody Homestay homestay) {
        Long hostId = Long.parseLong(userDetails.getUsername());
        Homestay existing = homestayService.getById(homestay.getId());
        if (existing == null || !existing.getHostId().equals(hostId)) {
            throw new BusinessException("无权操作此民宿");
        }
        homestayService.updateById(homestay);
        return Result.success();
    }

    @Operation(summary = "删除民宿")
    @DeleteMapping("/homestay/delete/{id}")
    public Result<Void> deleteHomestay(@AuthenticationPrincipal UserDetails userDetails,
                                       @PathVariable Long id) {
        Long hostId = Long.parseLong(userDetails.getUsername());
        Homestay existing = homestayService.getById(id);
        if (existing == null || !existing.getHostId().equals(hostId)) {
            throw new BusinessException("无权操作此民宿");
        }
        homestayService.removeById(id);
        return Result.success();
    }

    @Operation(summary = "添加房型")
    @PostMapping("/roomtype/add")
    public Result<Void> addRoomType(@AuthenticationPrincipal UserDetails userDetails,
                                    @RequestBody RoomType roomType) {
        Long hostId = Long.parseLong(userDetails.getUsername());
        Homestay homestay = homestayService.getById(roomType.getHomestayId());
        if (homestay == null || !homestay.getHostId().equals(hostId)) {
            throw new BusinessException("无权操作此民宿");
        }
        roomTypeService.save(roomType);
        return Result.success();
    }

    @Operation(summary = "更新房型")
    @PutMapping("/roomtype/update")
    public Result<Void> updateRoomType(@AuthenticationPrincipal UserDetails userDetails,
                                       @RequestBody RoomType roomType) {
        Long hostId = Long.parseLong(userDetails.getUsername());
        RoomType existing = roomTypeService.getById(roomType.getId());
        if (existing == null) {
            throw new BusinessException("房型不存在");
        }
        Homestay homestay = homestayService.getById(existing.getHomestayId());
        if (homestay == null || !homestay.getHostId().equals(hostId)) {
            throw new BusinessException("无权操作此房型");
        }
        roomTypeService.updateById(roomType);
        return Result.success();
    }

    @Operation(summary = "删除房型")
    @DeleteMapping("/roomtype/delete/{id}")
    public Result<Void> deleteRoomType(@AuthenticationPrincipal UserDetails userDetails,
                                       @PathVariable Long id) {
        Long hostId = Long.parseLong(userDetails.getUsername());
        RoomType existing = roomTypeService.getById(id);
        if (existing == null) {
            throw new BusinessException("房型不存在");
        }
        Homestay homestay = homestayService.getById(existing.getHomestayId());
        if (homestay == null || !homestay.getHostId().equals(hostId)) {
            throw new BusinessException("无权操作此房型");
        }
        roomTypeService.removeById(id);
        return Result.success();
    }

    @Operation(summary = "房东订单列表")
    @GetMapping("/booking/list")
    public Result<IPage<Booking>> listBooking(@AuthenticationPrincipal UserDetails userDetails,
                                              @RequestParam(defaultValue = "1") Integer current,
                                              @RequestParam(defaultValue = "10") Integer size,
                                              @RequestParam(required = false) Integer status) {
        Long hostId = Long.parseLong(userDetails.getUsername());
        return Result.success(bookingService.pageByHost(hostId, current, size, status));
    }

    @Operation(summary = "确认订单")
    @PostMapping("/booking/confirm/{id}")
    public Result<Void> confirmBooking(@AuthenticationPrincipal UserDetails userDetails,
                                       @PathVariable Long id) {
        Long hostId = Long.parseLong(userDetails.getUsername());
        bookingService.confirmBooking(hostId, id);
        return Result.success();
    }

    @Operation(summary = "回复评价")
    @PostMapping("/review/reply/{id}")
    public Result<Void> replyReview(@AuthenticationPrincipal UserDetails userDetails,
                                    @PathVariable Long id,
                                    @RequestParam String reply) {
        Long hostId = Long.parseLong(userDetails.getUsername());
        reviewService.replyReview(hostId, id, reply);
        return Result.success();
    }
}
