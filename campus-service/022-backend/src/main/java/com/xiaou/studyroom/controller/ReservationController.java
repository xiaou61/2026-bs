package com.xiaou.studyroom.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.studyroom.common.Result;
import com.xiaou.studyroom.entity.Reservation;
import com.xiaou.studyroom.exception.BusinessException;
import com.xiaou.studyroom.service.ReservationService;
import com.xiaou.studyroom.utils.AuthHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/reservation")
@CrossOrigin
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private AuthHelper authHelper;

    @PostMapping
    public Result<String> createReservation(@RequestHeader("Authorization") String token,
                                          @RequestBody Map<String, Object> requestMap) {
        try {
            Long userId = authHelper.getUserId(token);
            Long seatId = Long.valueOf(requestMap.get("seatId").toString());
            String startTimeStr = requestMap.get("startTime").toString();
            String endTimeStr = requestMap.get("endTime").toString();

            java.time.LocalDateTime startTime = parseDateTime(startTimeStr);
            java.time.LocalDateTime endTime = parseDateTime(endTimeStr);

            if (reservationService.createReservation(userId, seatId, startTime, endTime)) {
                return Result.success("预约成功");
            }
            return Result.error("预约失败，请检查预约时间或座位状态");
        } catch (Exception e) {
            return Result.error("预约失败：" + e.getMessage());
        }
    }

    @PutMapping("/{id}/cancel")
    public Result<String> cancelReservation(@RequestHeader("Authorization") String token,
                                           @PathVariable Long id) {
        Long userId = authHelper.getUserId(token);
        if (reservationService.cancelReservation(id, userId)) {
            return Result.success("预约取消成功");
        }
        return Result.error("取消失败，只能取消预约开始前1小时的预约");
    }

    @PostMapping("/checkin")
    public Result<String> checkIn(@RequestHeader("Authorization") String token,
                                 @RequestBody Map<String, String> requestMap) {
        try {
            Long userId = authHelper.getUserId(token);
            String qrcodeContent = requestMap.get("qrcodeContent");

            if (reservationService.checkIn(qrcodeContent, userId)) {
                return Result.success("签到成功");
            }
            return Result.error("签到失败，请检查二维码或签到时间");
        } catch (Exception e) {
            return Result.error("签到失败：" + e.getMessage());
        }
    }

    @PutMapping("/{id}/checkin")
    public Result<String> checkInById(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        Long userId = authHelper.getUserId(token);
        if (reservationService.checkInByReservationId(id, userId)) {
            return Result.success("签到成功");
        }
        return Result.error("签到失败，请检查签到时间");
    }

    @PutMapping("/{id}/end")
    public Result<String> endReservation(@RequestHeader("Authorization") String token,
                                        @PathVariable Long id) {
        Long userId = authHelper.getUserId(token);
        if (reservationService.endReservation(id, userId)) {
            return Result.success("使用结束");
        }
        return Result.error("结束失败");
    }

    @GetMapping("/my")
    public Result<Page<Reservation>> getMyReservations(@RequestHeader("Authorization") String token,
                                                      @RequestParam(required = false) Integer current,
                                                      @RequestParam(required = false) Integer page,
                                                      @RequestParam(defaultValue = "10") int size,
                                                      @RequestParam(required = false) Integer status,
                                                      @RequestParam(required = false) String startDate,
                                                      @RequestParam(required = false) String endDate) {
        Long userId = authHelper.getUserId(token);
        int pageNo = current != null ? current : (page != null ? page : 1);
        Page<Reservation> reservationPage = reservationService.getUserReservations(
                userId,
                pageNo,
                size,
                status,
                parseDateStart(startDate),
                parseDateEnd(endDate)
        );
        return Result.success(reservationPage);
    }

    @GetMapping("/{id}")
    public Result<Reservation> getReservationById(@RequestHeader("Authorization") String token,
                                                 @PathVariable Long id) {
        Long userId = authHelper.getUserId(token);
        Reservation reservation = reservationService.getReservationDetail(id);
        if (reservation != null && (reservation.getUserId().equals(userId) || authHelper.isAdmin(token))) {
            return Result.success(reservation);
        }
        return Result.error("预约记录不存在或无权访问");
    }

    @GetMapping("/{id}/qrcode")
    public Result<Map<String, Object>> getReservationQRCode(@RequestHeader("Authorization") String token,
                                                           @PathVariable Long id) {
        Long userId = authHelper.getUserId(token);
        Reservation reservation = reservationService.getReservationDetail(id);

        if (reservation == null || !reservation.getUserId().equals(userId)) {
            return Result.error("预约记录不存在或无权访问");
        }

        if (reservation.getStatus() != 1) {
            return Result.error("只能为已预约状态的记录生成二维码");
        }

        Map<String, Object> result = Map.of(
            "qrcodeContent", reservation.getQrcodeContent(),
            "reservation", reservation
        );

        return Result.success(result);
    }

    @GetMapping("/seat/{seatId}/current")
    public Result<Object> getCurrentReservation(@PathVariable Long seatId) {
        var currentReservations = reservationService.getCurrentReservations(seatId);
        return Result.success(currentReservations);
    }

    @GetMapping({"/page", "/all"})
    public Result<Page<Reservation>> getReservationPage(@RequestHeader("Authorization") String token,
                                                        @RequestParam(required = false) Integer current,
                                                        @RequestParam(required = false) Integer page,
                                                        @RequestParam(defaultValue = "10") int size,
                                                        @RequestParam(required = false) Long userId,
                                                        @RequestParam(required = false) Long roomId,
                                                        @RequestParam(required = false) Integer status,
                                                        @RequestParam(required = false) String startDate,
                                                        @RequestParam(required = false) String endDate) {
        authHelper.requireAdmin(token);
        int pageNo = current != null ? current : (page != null ? page : 1);
        Page<Reservation> reservationPage = reservationService.getReservationPage(
                pageNo,
                size,
                userId,
                roomId,
                status,
                parseDateStart(startDate),
                parseDateEnd(endDate)
        );
        return Result.success(reservationPage);
    }

    private java.time.LocalDateTime parseDateTime(String value) {
        if (value == null || value.isBlank()) {
            throw new BusinessException(400, "预约时间不能为空");
        }
        return value.contains("T")
                ? java.time.LocalDateTime.parse(value)
                : java.time.LocalDateTime.parse(value.replace(" ", "T"));
    }

    private java.time.LocalDateTime parseDateStart(String value) {
        return value == null || value.isBlank() ? null : LocalDate.parse(value).atStartOfDay();
    }

    private java.time.LocalDateTime parseDateEnd(String value) {
        return value == null || value.isBlank() ? null : LocalDate.parse(value).atTime(23, 59, 59);
    }
}
