package com.xiaou.rice.modules.booking.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaou.rice.common.api.Result;
import com.xiaou.rice.common.api.ResultCode;
import com.xiaou.rice.modules.booking.dto.CreateBookingRequest;
import com.xiaou.rice.modules.booking.entity.Booking;
import com.xiaou.rice.modules.booking.enums.BookingStatus;
import com.xiaou.rice.modules.booking.service.BookingService;
import com.xiaou.rice.modules.booking.service.BookingStatusLogService;
import com.xiaou.rice.security.SecurityUtil;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;
    private final BookingStatusLogService bookingStatusLogService;

    public BookingController(BookingService bookingService, BookingStatusLogService bookingStatusLogService) {
        this.bookingService = bookingService;
        this.bookingStatusLogService = bookingStatusLogService;
    }

    @GetMapping
    public Result<List<Booking>> list() {
        Long uid = SecurityUtil.currentUserId();
        List<Booking> bookings = bookingService.list(new LambdaQueryWrapper<Booking>()
                .eq(Booking::getFarmerId, uid)
                .orderByDesc(Booking::getCreatedAt));
        return Result.ok(bookings);
    }

    @PostMapping
    public Result<Booking> create(@Valid @RequestBody CreateBookingRequest req) {
        Long uid = SecurityUtil.currentUserId();
        Booking booking = new Booking();
        booking.setFarmerId(uid);
        booking.setPlotId(req.getPlotId());
        booking.setExpectDate(req.getExpectDate());
        booking.setTimeWindow(req.getTimeWindow());
        booking.setArea(req.getArea());
        booking.setAddress(req.getAddress());
        booking.setLatitude(req.getLatitude());
        booking.setLongitude(req.getLongitude());
        booking.setRemark(req.getRemark());
        booking.setStatus(BookingStatus.PENDING);
        bookingService.save(booking);
        bookingStatusLogService.log(booking.getId(), BookingStatus.PENDING, "创建预约");
        return Result.ok(booking);
    }

    @PatchMapping("/{id}/cancel")
    public Result<Boolean> cancel(@PathVariable Long id) {
        Long uid = SecurityUtil.currentUserId();
        Booking booking = bookingService.getById(id);
        if (booking == null || !booking.getFarmerId().equals(uid)) {
            return Result.fail(ResultCode.NOT_FOUND);
        }
        if (booking.getStatus() == BookingStatus.IN_PROGRESS || booking.getStatus() == BookingStatus.SETTLED) {
            return Result.fail(ResultCode.BUSINESS_ERROR.getCode(), "作业中/已结算不可取消");
        }
        booking.setStatus(BookingStatus.CANCELLED);
        boolean ok = bookingService.updateById(booking);
        if (ok) bookingStatusLogService.log(id, BookingStatus.CANCELLED, "用户取消");
        return Result.ok(ok);
    }

    @PatchMapping("/{id}/assign")
    public Result<Boolean> assignMachine(@PathVariable Long id, @RequestParam Long machineId) {
        Booking booking = bookingService.getById(id);
        if (booking == null) return Result.fail(ResultCode.NOT_FOUND);
        booking.setMachineId(machineId);
        booking.setStatus(BookingStatus.CONFIRMED);
        boolean ok = bookingService.updateById(booking);
        if (ok) bookingStatusLogService.log(id, BookingStatus.CONFIRMED, "指派设备");
        return Result.ok(ok);
    }

    @PatchMapping("/{id}/start")
    public Result<Boolean> start(@PathVariable Long id) {
        Booking booking = bookingService.getById(id);
        if (booking == null) return Result.fail(ResultCode.NOT_FOUND);
        if (booking.getStatus() != BookingStatus.CONFIRMED) {
            return Result.fail(ResultCode.BUSINESS_ERROR.getCode(), "仅已确认订单可开工");
        }
        booking.setStatus(BookingStatus.IN_PROGRESS);
        boolean ok = bookingService.updateById(booking);
        if (ok) bookingStatusLogService.log(id, BookingStatus.IN_PROGRESS, "开始作业");
        return Result.ok(ok);
    }

    @PatchMapping("/{id}/finish")
    public Result<Boolean> finish(@PathVariable Long id, @RequestParam(required = false) BigDecimal settleAmount) {
        Booking booking = bookingService.getById(id);
        if (booking == null) return Result.fail(ResultCode.NOT_FOUND);
        if (booking.getStatus() != BookingStatus.IN_PROGRESS) {
            return Result.fail(ResultCode.BUSINESS_ERROR.getCode(), "仅作业中订单可完工");
        }
        booking.setStatus(BookingStatus.SETTLED);
        if (settleAmount != null) {
            booking.setSettleAmount(settleAmount);
        }
        boolean ok = bookingService.updateById(booking);
        if (ok) bookingStatusLogService.log(id, BookingStatus.SETTLED, "完工结算");
        return Result.ok(ok);
    }
}
