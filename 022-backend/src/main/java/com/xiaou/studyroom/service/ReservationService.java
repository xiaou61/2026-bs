package com.xiaou.studyroom.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaou.studyroom.entity.Reservation;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationService extends IService<Reservation> {

    boolean createReservation(Long userId, Long seatId, LocalDateTime startTime, LocalDateTime endTime);

    boolean cancelReservation(Long reservationId, Long userId);

    boolean checkIn(String qrcodeContent, Long userId);

    boolean endReservation(Long reservationId, Long userId);

    Page<Reservation> getUserReservations(Long userId, int current, int size, Integer status);

    List<Reservation> getCurrentReservations(Long seatId);

    List<Reservation> getReservationsByTimeRange(Long seatId, LocalDateTime startTime, LocalDateTime endTime);

    boolean checkSeatAvailable(Long seatId, LocalDateTime startTime, LocalDateTime endTime);

    boolean checkUserCanReserve(Long userId, LocalDateTime startTime);

    void releaseExpiredReservations();

    String generateQRCode(Long reservationId, Long userId, String seatNumber);

    boolean validateQRCode(String qrcodeContent, Long reservationId, Long userId);
}