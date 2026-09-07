package com.xiaou.rice.modules.booking.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaou.rice.common.api.Result;
import com.xiaou.rice.common.api.ResultCode;
import com.xiaou.rice.common.exception.BusinessException;
import com.xiaou.rice.modules.booking.dto.CreateBookingRequest;
import com.xiaou.rice.modules.booking.entity.Booking;
import com.xiaou.rice.modules.booking.enums.BookingStatus;
import com.xiaou.rice.modules.booking.service.BookingService;
import com.xiaou.rice.modules.booking.service.BookingStatusLogService;
import com.xiaou.rice.modules.farm.entity.FarmPlot;
import com.xiaou.rice.modules.farm.service.FarmPlotService;
import com.xiaou.rice.modules.machine.entity.Machine;
import com.xiaou.rice.modules.machine.service.MachineService;
import com.xiaou.rice.security.SecurityUtil;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private static final int ROLE_FARMER = 1;
    private static final int ROLE_DRIVER = 2;
    private static final int ROLE_ADMIN = 9;

    private final BookingService bookingService;
    private final BookingStatusLogService bookingStatusLogService;
    private final FarmPlotService farmPlotService;
    private final MachineService machineService;

    public BookingController(BookingService bookingService,
                             BookingStatusLogService bookingStatusLogService,
                             FarmPlotService farmPlotService,
                             MachineService machineService) {
        this.bookingService = bookingService;
        this.bookingStatusLogService = bookingStatusLogService;
        this.farmPlotService = farmPlotService;
        this.machineService = machineService;
    }

    @GetMapping
    public Result<List<Booking>> list() {
        requireRole(ROLE_FARMER, "仅农户可查看预约");
        Long uid = SecurityUtil.currentUserId();
        List<Booking> bookings = bookingService.list(new LambdaQueryWrapper<Booking>()
                .eq(Booking::getFarmerId, uid)
                .orderByDesc(Booking::getCreatedAt));
        return Result.ok(bookings);
    }

    @PostMapping
    public Result<Booking> create(@Valid @RequestBody CreateBookingRequest req) {
        requireRole(ROLE_FARMER, "仅农户可创建预约");
        Long uid = SecurityUtil.currentUserId();
        FarmPlot plot = farmPlotService.getById(req.getPlotId());
        if (plot == null || !uid.equals(plot.getUserId())) {
            throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "地块不存在或无权限");
        }
        Booking booking = new Booking();
        booking.setFarmerId(uid);
        booking.setPlotId(req.getPlotId());
        booking.setExpectDate(req.getExpectDate());
        booking.setTimeWindow(req.getTimeWindow());
        booking.setArea(req.getArea() != null ? req.getArea() : plot.getArea());
        booking.setAddress(req.getAddress());
        booking.setLatitude(req.getLatitude());
        booking.setLongitude(req.getLongitude());
        booking.setRemark(req.getRemark());
        booking.setStatus(BookingStatus.PENDING.name());
        bookingService.save(booking);
        bookingStatusLogService.log(booking.getId(), BookingStatus.PENDING, "创建预约");
        return Result.ok(booking);
    }

    @PatchMapping("/{id}/cancel")
    public Result<Boolean> cancel(@PathVariable Long id) {
        requireRole(ROLE_FARMER, "仅农户可取消预约");
        Long uid = SecurityUtil.currentUserId();
        Booking booking = bookingService.getById(id);
        if (booking == null || !booking.getFarmerId().equals(uid)) {
            throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "预约不存在或无权限");
        }
        if (BookingStatus.IN_PROGRESS.name().equals(booking.getStatus()) || BookingStatus.SETTLED.name().equals(booking.getStatus())) {
            return Result.fail(ResultCode.BUSINESS_ERROR.getCode(), "作业中/已结算不可取消");
        }
        booking.setStatus(BookingStatus.CANCELLED.name());
        boolean ok = bookingService.updateById(booking);
        if (ok) bookingStatusLogService.log(id, BookingStatus.CANCELLED, "用户取消");
        return Result.ok(ok);
    }

    @PatchMapping("/{id}/assign")
    public Result<Boolean> assignMachine(@PathVariable Long id, @RequestParam Long machineId) {
        requireRole(ROLE_ADMIN, "仅管理员可指派设备");
        Booking booking = bookingService.getById(id);
        if (booking == null) {
            throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "预约不存在");
        }
        if (!BookingStatus.PENDING.name().equals(booking.getStatus())) {
            return Result.fail(ResultCode.BUSINESS_ERROR.getCode(), "仅待处理预约可指派设备");
        }
        Machine machine = machineService.getById(machineId);
        if (machine == null) {
            throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "设备不存在");
        }
        if (!Integer.valueOf(1).equals(machine.getStatus())) {
            return Result.fail(ResultCode.BUSINESS_ERROR.getCode(), "设备当前不可用");
        }
        booking.setMachineId(machineId);
        booking.setStatus(BookingStatus.CONFIRMED.name());
        boolean ok = bookingService.updateById(booking);
        if (ok) bookingStatusLogService.log(id, BookingStatus.CONFIRMED, "指派设备");
        return Result.ok(ok);
    }

    @PatchMapping("/{id}/start")
    public Result<Boolean> start(@PathVariable Long id) {
        Long uid = SecurityUtil.currentUserId();
        Booking booking = bookingService.getById(id);
        if (booking == null) {
            throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "预约不存在");
        }
        if (!BookingStatus.CONFIRMED.name().equals(booking.getStatus())) {
            return Result.fail(ResultCode.BUSINESS_ERROR.getCode(), "仅已确认订单可开工");
        }
        if (!canOperateAssignedMachine(booking, uid)) {
            throw new BusinessException(ResultCode.FORBIDDEN.getCode(), "仅指派机手或管理员可开工");
        }
        booking.setStatus(BookingStatus.IN_PROGRESS.name());
        boolean ok = bookingService.updateById(booking);
        if (ok) bookingStatusLogService.log(id, BookingStatus.IN_PROGRESS, "开始作业");
        return Result.ok(ok);
    }

    @PatchMapping("/{id}/finish")
    public Result<Boolean> finish(@PathVariable Long id, @RequestParam(required = false) BigDecimal settleAmount) {
        Long uid = SecurityUtil.currentUserId();
        Booking booking = bookingService.getById(id);
        if (booking == null) {
            throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "预约不存在");
        }
        if (!BookingStatus.IN_PROGRESS.name().equals(booking.getStatus())) {
            return Result.fail(ResultCode.BUSINESS_ERROR.getCode(), "仅作业中订单可完工");
        }
        if (!canOperateAssignedMachine(booking, uid)) {
            throw new BusinessException(ResultCode.FORBIDDEN.getCode(), "仅指派机手或管理员可完工");
        }
        booking.setStatus(BookingStatus.SETTLED.name());
        if (settleAmount != null) {
            booking.setSettleAmount(settleAmount);
        }
        boolean ok = bookingService.updateById(booking);
        if (ok) bookingStatusLogService.log(id, BookingStatus.SETTLED, "完工结算");
        return Result.ok(ok);
    }

    private void requireRole(int role, String message) {
        if (!Integer.valueOf(role).equals(SecurityUtil.currentRole())) {
            throw new BusinessException(ResultCode.FORBIDDEN.getCode(), message);
        }
    }

    private boolean canOperateAssignedMachine(Booking booking, Long userId) {
        Integer currentRole = SecurityUtil.currentRole();
        if (Integer.valueOf(ROLE_ADMIN).equals(currentRole)) {
            return true;
        }
        if (!Integer.valueOf(ROLE_DRIVER).equals(currentRole) || booking.getMachineId() == null) {
            return false;
        }
        Machine machine = machineService.getById(booking.getMachineId());
        return machine != null && userId != null && userId.equals(machine.getOwnerId());
    }
}
