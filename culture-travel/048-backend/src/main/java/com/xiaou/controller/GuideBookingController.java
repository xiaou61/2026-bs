package com.xiaou.controller;

import com.xiaou.common.Result;
import com.xiaou.dto.GuideBookingDTO;
import com.xiaou.service.GuideBookingService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/guide-booking")
@RequiredArgsConstructor
public class GuideBookingController {
    private final GuideBookingService guideBookingService;

    @PostMapping
    public Result<?> create(HttpServletRequest request, @RequestBody GuideBookingDTO dto) {
        Long userId = (Long) request.getAttribute("userId");
        guideBookingService.create(userId, dto);
        return Result.success();
    }

    @GetMapping("/my")
    public Result<?> my(HttpServletRequest request,
                        @RequestParam(defaultValue = "1") int current,
                        @RequestParam(defaultValue = "10") int size) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(guideBookingService.pageByUser(userId, current, size));
    }

    @PostMapping("/{id}/cancel")
    public Result<?> cancel(@PathVariable Long id) {
        guideBookingService.cancel(id);
        return Result.success();
    }
}
