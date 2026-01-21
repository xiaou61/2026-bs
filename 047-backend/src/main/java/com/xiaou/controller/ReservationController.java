package com.xiaou.controller;

import com.xiaou.common.Result;
import com.xiaou.dto.ReservationDTO;
import com.xiaou.entity.Reservation;
import com.xiaou.service.ReservationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public Result create(HttpServletRequest request, @RequestBody ReservationDTO dto) {
        Long userId = (Long) request.getAttribute("userId");
        reservationService.createReservation(userId, dto);
        return Result.success();
    }

    @GetMapping("/my")
    public Result my(HttpServletRequest request,
                     @RequestParam(defaultValue = "1") int current,
                     @RequestParam(defaultValue = "10") int size) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(reservationService.getMyReservations(userId, current, size));
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        return Result.success(reservationService.getById(id));
    }

    @PutMapping("/{id}/cancel")
    public Result cancel(@PathVariable Long id) {
        Reservation r = reservationService.getById(id);
        if (r != null && r.getStatus() == 0) {
            r.setStatus(4);
            reservationService.updateById(r);
        }
        return Result.success();
    }
}
