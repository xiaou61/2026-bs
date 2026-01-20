package com.xiaou.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaou.common.Result;
import com.xiaou.dto.BookingDTO;
import com.xiaou.entity.Booking;
import com.xiaou.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "预约管理")
@RestController
@RequestMapping("/api/booking")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @Operation(summary = "创建预约")
    @PostMapping
    public Result<Void> create(HttpServletRequest request, @Valid @RequestBody BookingDTO dto) {
        Long userId = (Long) request.getAttribute("userId");
        bookingService.createBooking(userId, dto);
        return Result.success();
    }

    @Operation(summary = "取消预约")
    @PostMapping("/{id}/cancel")
    public Result<Void> cancel(HttpServletRequest request, @PathVariable Long id) {
        Long userId = (Long) request.getAttribute("userId");
        bookingService.cancelBooking(userId, id);
        return Result.success();
    }

    @Operation(summary = "分页获取我的预约")
    @GetMapping("/page")
    public Result<IPage<Booking>> page(HttpServletRequest request,
                                       @RequestParam(defaultValue = "1") Integer current,
                                       @RequestParam(defaultValue = "10") Integer size,
                                       @RequestParam(required = false) Integer status) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(bookingService.pageByUser(userId, current, size, status));
    }

    @Operation(summary = "获取预约详情")
    @GetMapping("/{id}")
    public Result<Booking> detail(HttpServletRequest request, @PathVariable Long id) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(bookingService.detail(userId, id));
    }
}
