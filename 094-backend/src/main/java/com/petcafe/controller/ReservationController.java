package com.petcafe.controller;

import com.petcafe.common.Result;
import com.petcafe.dto.ReservationDTO;
import com.petcafe.service.ReservationService;
import com.petcafe.utils.AuthUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/reservation")
public class ReservationController {

    @Resource
    private ReservationService reservationService;

    @GetMapping("/list")
    public Result<?> list(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) Long shopId,
                          @RequestParam(required = false) String status,
                          HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        String role = (String) request.getAttribute("role");
        return Result.success(reservationService.page(pageNum, pageSize, shopId, status, userId, role));
    }

    @PostMapping("/create")
    public Result<?> create(@RequestBody ReservationDTO dto, HttpServletRequest request) {
        return Result.success(reservationService.create((Long) request.getAttribute("userId"), dto));
    }

    @PutMapping("/status/{id}")
    public Result<?> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> params, HttpServletRequest request) {
        AuthUtils.requireAdmin((String) request.getAttribute("role"));
        reservationService.updateStatus(id, params.get("status"), params.get("managerRemark"));
        return Result.success();
    }

    @PutMapping("/cancel/{id}")
    public Result<?> cancel(@PathVariable Long id, HttpServletRequest request) {
        reservationService.cancel(id, (Long) request.getAttribute("userId"), (String) request.getAttribute("role"));
        return Result.success();
    }
}
