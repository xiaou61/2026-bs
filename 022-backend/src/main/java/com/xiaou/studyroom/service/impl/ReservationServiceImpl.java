package com.xiaou.studyroom.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.studyroom.entity.Reservation;
import com.xiaou.studyroom.entity.Seat;
import com.xiaou.studyroom.entity.User;
import com.xiaou.studyroom.mapper.ReservationMapper;
import com.xiaou.studyroom.service.ReservationService;
import com.xiaou.studyroom.service.SeatService;
import com.xiaou.studyroom.service.UserService;
import com.xiaou.studyroom.utils.QRCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationServiceImpl extends ServiceImpl<ReservationMapper, Reservation> implements ReservationService {

    @Autowired
    private UserService userService;

    @Autowired
    private SeatService seatService;

    @Autowired
    private QRCodeUtil qrCodeUtil;

    @Override
    @Transactional
    public boolean createReservation(Long userId, Long seatId, LocalDateTime startTime, LocalDateTime endTime) {
        // 检查用户是否存在且可用
        User user = userService.getById(userId);
        if (user == null || user.getStatus() != 1) {
            return false;
        }

        // 检查用户是否可以预约
        if (!checkUserCanReserve(userId, startTime)) {
            return false;
        }

        // 检查座位是否存在且可用
        Seat seat = seatService.getById(seatId);
        if (seat == null || seat.getSeatStatus() != 1) {
            return false;
        }

        // 检查座位在预约时间段是否可用
        if (!checkSeatAvailable(seatId, startTime, endTime)) {
            return false;
        }

        // 检查预约时间是否合理
        LocalDateTime now = LocalDateTime.now();
        if (startTime.isBefore(now.plusHours(1))) {
            return false; // 提前最少1小时预约
        }
        if (startTime.isAfter(now.plusDays(2))) {
            return false; // 提前最多2天预约
        }
        if (Duration.between(startTime, endTime).toHours() > 4) {
            return false; // 单次预约最长4小时
        }

        // 创建预约记录
        Reservation reservation = new Reservation();
        reservation.setUserId(userId);
        reservation.setSeatId(seatId);
        reservation.setReservationTime(now);
        reservation.setStartTime(startTime);
        reservation.setEndTime(endTime);
        reservation.setStatus(1); // 已预约状态

        // 生成二维码内容
        String qrcodeContent = generateQRCode(null, userId, seat.getSeatNumber());
        reservation.setQrcodeContent(qrcodeContent);

        // 保存预约记录
        boolean result = save(reservation);

        if (result) {
            // 更新二维码内容，包含预约ID
            qrcodeContent = generateQRCode(reservation.getId(), userId, seat.getSeatNumber());
            reservation.setQrcodeContent(qrcodeContent);
            updateById(reservation);

            // 更新座位状态
            seatService.updateSeatStatus(seatId, 2); // 设置为占用状态
        }

        return result;
    }

    @Override
    @Transactional
    public boolean cancelReservation(Long reservationId, Long userId) {
        Reservation reservation = getById(reservationId);
        if (reservation == null || !reservation.getUserId().equals(userId)) {
            return false;
        }

        if (reservation.getStatus() != 1) {
            return false; // 只能取消已预约状态的记录
        }

        // 检查是否可以取消（预约开始前1小时可以取消）
        LocalDateTime now = LocalDateTime.now();
        if (now.plusHours(1).isAfter(reservation.getStartTime())) {
            return false;
        }

        reservation.setStatus(4); // 已取消
        boolean result = updateById(reservation);

        if (result) {
            // 释放座位
            seatService.updateSeatStatus(reservation.getSeatId(), 1);

            // 扣除信用分
            userService.updateUserCredit(userId, -1, "取消预约");
        }

        return result;
    }

    @Override
    @Transactional
    public boolean checkIn(String qrcodeContent, Long userId) {
        if (StrUtil.isBlank(qrcodeContent)) {
            return false;
        }

        // 解析二维码内容
        QueryWrapper<Reservation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("qrcode_content", qrcodeContent)
                   .eq("user_id", userId)
                   .eq("status", 1);

        Reservation reservation = getOne(queryWrapper);
        if (reservation == null) {
            return false;
        }

        LocalDateTime now = LocalDateTime.now();

        // 检查签到时间是否合理（预约开始后15分钟内）
        if (now.isBefore(reservation.getStartTime().minusMinutes(15)) ||
            now.isAfter(reservation.getStartTime().plusMinutes(15))) {
            return false;
        }

        // 更新签到时间
        reservation.setCheckInTime(now);
        reservation.setStatus(2); // 已签到

        boolean result = updateById(reservation);

        if (result) {
            // 增加信用分
            userService.updateUserCredit(userId, 2, "正常签到");
        }

        return result;
    }

    @Override
    @Transactional
    public boolean endReservation(Long reservationId, Long userId) {
        Reservation reservation = getById(reservationId);
        if (reservation == null || !reservation.getUserId().equals(userId)) {
            return false;
        }

        if (reservation.getStatus() != 2) {
            return false; // 只能结束已签到的预约
        }

        LocalDateTime now = LocalDateTime.now();
        reservation.setActualEndTime(now);
        reservation.setStatus(3); // 已完成

        boolean result = updateById(reservation);

        if (result) {
            // 释放座位
            seatService.updateSeatStatus(reservation.getSeatId(), 1);

            // 计算信用分奖励
            long usedMinutes = Duration.between(reservation.getCheckInTime(), now).toMinutes();
            long expectedMinutes = Duration.between(reservation.getStartTime(), reservation.getEndTime()).toMinutes();

            if (usedMinutes >= expectedMinutes * 0.8) {
                // 使用时长超过80%，给予额外奖励
                userService.updateUserCredit(userId, 1, "提前结束使用");
            }
        }

        return result;
    }

    @Override
    public Page<Reservation> getUserReservations(Long userId, int current, int size, Integer status) {
        Page<Reservation> page = new Page<>(current, size);
        QueryWrapper<Reservation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId)
                   .orderByDesc("create_time");

        if (status != null) {
            queryWrapper.eq("status", status);
        }

        return page(page, queryWrapper);
    }

    @Override
    public List<Reservation> getCurrentReservations(Long seatId) {
        LocalDateTime now = LocalDateTime.now();
        QueryWrapper<Reservation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("seat_id", seatId)
                   .eq("status", 2) // 已签到状态
                   .le("start_time", now)
                   .ge("end_time", now);

        return list(queryWrapper);
    }

    @Override
    public List<Reservation> getReservationsByTimeRange(Long seatId, LocalDateTime startTime, LocalDateTime endTime) {
        QueryWrapper<Reservation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("seat_id", seatId)
                   .in("status", 1, 2) // 已预约或已签到
                   .and(wrapper -> wrapper
                       .and(w -> w.le("start_time", endTime).ge("end_time", startTime))
                       .or(w -> w.le("start_time", startTime).ge("end_time", startTime))
                       .or(w -> w.ge("start_time", startTime).le("end_time", endTime))
                   );

        return list(queryWrapper);
    }

    @Override
    public boolean checkSeatAvailable(Long seatId, LocalDateTime startTime, LocalDateTime endTime) {
        List<Reservation> conflictingReservations = getReservationsByTimeRange(seatId, startTime, endTime);
        return conflictingReservations.isEmpty();
    }

    @Override
    public boolean checkUserCanReserve(Long userId, LocalDateTime startTime) {
        User user = userService.getById(userId);
        if (user == null || user.getStatus() != 1) {
            return false;
        }

        // 检查信用分限制
        if (user.getCreditScore() < 30) {
            return false; // 信用分太低，禁止预约
        }

        // 检查是否已有有效预约
        LocalDateTime now = LocalDateTime.now();
        QueryWrapper<Reservation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId)
                   .in("status", 1, 2) // 已预约或已签到
                   .ge("end_time", now);

        long activeReservationCount = count(queryWrapper);
        if (activeReservationCount > 0) {
            return false; // 已有有效预约
        }

        // 检查当天预约次数限制（信用分低的情况下）
        if (user.getCreditScore() < 60) {
            LocalDateTime dayStart = startTime.toLocalDate().atStartOfDay();
            LocalDateTime dayEnd = startTime.toLocalDate().atTime(23, 59, 59);

            QueryWrapper<Reservation> dailyQuery = new QueryWrapper<>();
            dailyQuery.eq("user_id", userId)
                     .eq("status", 1)
                     .between("start_time", dayStart, dayEnd);

            long dailyReservationCount = count(dailyQuery);
            if (dailyReservationCount >= 1) {
                return false; // 信用分低时每天只能预约1次
            }
        }

        return true;
    }

    @Override
    @Transactional
    public void releaseExpiredReservations() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime timeoutThreshold = now.minusMinutes(15);

        // 查找超时未签到的预约
        QueryWrapper<Reservation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", 1) // 已预约状态
                   .lt("start_time", timeoutThreshold);

        List<Reservation> expiredReservations = list(queryWrapper);

        for (Reservation reservation : expiredReservations) {
            // 标记为违约
            reservation.setStatus(5); // 违约
            updateById(reservation);

            // 释放座位
            seatService.updateSeatStatus(reservation.getSeatId(), 1);

            // 扣除信用分
            userService.updateUserCredit(reservation.getUserId(), -5, "超时未签到");
        }
    }

    @Override
    public String generateQRCode(Long reservationId, Long userId, String seatNumber) {
        if (reservationId == null) {
            return qrCodeUtil.generateQRCodeContent(System.currentTimeMillis(), userId, seatNumber);
        }
        return qrCodeUtil.generateQRCodeContent(reservationId, userId, seatNumber);
    }

    @Override
    public boolean validateQRCode(String qrcodeContent, Long reservationId, Long userId) {
        return qrCodeUtil.validateQRCodeContent(qrcodeContent, reservationId, userId);
    }
}