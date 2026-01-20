package com.xiaou.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaou.common.Result;
import com.xiaou.dto.BookingDTO;
import com.xiaou.entity.Booking;
import com.xiaou.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Tag(name = "订单管理")
@RestController
@RequestMapping("/api/booking")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @Operation(summary = "创建订单")
    @PostMapping("/create")
    public Result<Void> create(@AuthenticationPrincipal UserDetails userDetails,
                               @RequestBody BookingDTO dto) {
        Long userId = Long.parseLong(userDetails.getUsername());
        bookingService.createBooking(userId, dto);
        return Result.success();
    }

    @Operation(summary = "取消订单")
    @PostMapping("/cancel/{id}")
    public Result<Void> cancel(@AuthenticationPrincipal UserDetails userDetails,
                               @PathVariable Long id,
                               @RequestParam(required = false) String reason) {
        Long userId = Long.parseLong(userDetails.getUsername());
        bookingService.cancelBooking(userId, id, reason);
        return Result.success();
    }

    @Operation(summary = "我的订单列表")
    @GetMapping("/my")
    public Result<IPage<Booking>> myBookings(@AuthenticationPrincipal UserDetails userDetails,
                                             @RequestParam(defaultValue = "1") Integer current,
                                             @RequestParam(defaultValue = "10") Integer size,
                                             @RequestParam(required = false) Integer status) {
        Long userId = Long.parseLong(userDetails.getUsername());
        return Result.success(bookingService.pageByUser(userId, current, size, status));
    }

    @Operation(summary = "订单详情")
    @GetMapping("/detail/{id}")
    public Result<Booking> detail(@AuthenticationPrincipal UserDetails userDetails,
                                  @PathVariable Long id) {
        Long userId = Long.parseLong(userDetails.getUsername());
        return Result.success(bookingService.detail(userId, id));
    }
}
