package com.xiaou.studyroom.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.studyroom.common.Result;
import com.xiaou.studyroom.entity.Reservation;
import com.xiaou.studyroom.service.ReservationService;
import com.xiaou.studyroom.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/reservation")
@CrossOrigin
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping
    public Result<String> createReservation(@RequestHeader("Authorization") String token,
                                          @RequestBody Map<String, Object> requestMap) {
        try {
            Long userId = getUserIdFromToken(token);
            Long seatId = Long.valueOf(requestMap.get("seatId").toString());
            String startTimeStr = requestMap.get("startTime").toString();
            String endTimeStr = requestMap.get("endTime").toString();

            java.time.LocalDateTime startTime = java.time.LocalDateTime.parse(startTimeStr);
            java.time.LocalDateTime endTime = java.time.LocalDateTime.parse(endTimeStr);

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
        Long userId = getUserIdFromToken(token);
        if (reservationService.cancelReservation(id, userId)) {
            return Result.success("预约取消成功");
        }
        return Result.error("取消失败，只能取消预约开始前1小时的预约");
    }

    @PostMapping("/checkin")
    public Result<String> checkIn(@RequestHeader("Authorization") String token,
                                 @RequestBody Map<String, String> requestMap) {
        try {
            Long userId = getUserIdFromToken(token);
            String qrcodeContent = requestMap.get("qrcodeContent");

            if (reservationService.checkIn(qrcodeContent, userId)) {
                return Result.success("签到成功");
            }
            return Result.error("签到失败，请检查二维码或签到时间");
        } catch (Exception e) {
            return Result.error("签到失败：" + e.getMessage());
        }
    }

    @PutMapping("/{id}/end")
    public Result<String> endReservation(@RequestHeader("Authorization") String token,
                                        @PathVariable Long id) {
        Long userId = getUserIdFromToken(token);
        if (reservationService.endReservation(id, userId)) {
            return Result.success("使用结束");
        }
        return Result.error("结束失败");
    }

    @GetMapping("/my")
    public Result<Page<Reservation>> getMyReservations(@RequestHeader("Authorization") String token,
                                                      @RequestParam(defaultValue = "1") int current,
                                                      @RequestParam(defaultValue = "10") int size,
                                                      @RequestParam(required = false) Integer status) {
        Long userId = getUserIdFromToken(token);
        Page<Reservation> page = reservationService.getUserReservations(userId, current, size, status);
        return Result.success(page);
    }

    @GetMapping("/{id}")
    public Result<Reservation> getReservationById(@RequestHeader("Authorization") String token,
                                                 @PathVariable Long id) {
        Long userId = getUserIdFromToken(token);
        Reservation reservation = reservationService.getById(id);
        if (reservation != null && reservation.getUserId().equals(userId)) {
            return Result.success(reservation);
        }
        return Result.error("预约记录不存在或无权访问");
    }

    @GetMapping("/{id}/qrcode")
    public Result<Map<String, Object>> getReservationQRCode(@RequestHeader("Authorization") String token,
                                                           @PathVariable Long id) {
        Long userId = getUserIdFromToken(token);
        Reservation reservation = reservationService.getById(id);

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

    private Long getUserIdFromToken(String token) {
        try {
            String actualToken = token.replace("Bearer ", "");
            return jwtUtil.getUserIdFromToken(actualToken);
        } catch (Exception e) {
            throw new RuntimeException("Token解析失败", e);
        }
    }
}