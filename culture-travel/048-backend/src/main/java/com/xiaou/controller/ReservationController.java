package com.xiaou.controller;

import com.xiaou.common.Result;
import com.xiaou.dto.ReservationDTO;
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
    public Result<?> create(HttpServletRequest request, @RequestBody ReservationDTO dto) {
        Long userId = (Long) request.getAttribute("userId");
        reservationService.create(userId, dto);
        return Result.success();
    }

    @GetMapping("/my")
    public Result<?> my(HttpServletRequest request,
                        @RequestParam(defaultValue = "1") int current,
                        @RequestParam(defaultValue = "10") int size) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(reservationService.pageByUser(userId, current, size));
    }

    @PostMapping("/{id}/cancel")
    public Result<?> cancel(@PathVariable Long id) {
        reservationService.cancel(id);
        return Result.success();
    }
}
