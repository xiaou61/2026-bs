package com.petcafe.controller;

import com.petcafe.common.Result;
import com.petcafe.entity.SeatInfo;
import com.petcafe.service.SeatService;
import com.petcafe.utils.AuthUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/seat")
public class SeatController {

    @Resource
    private SeatService seatService;

    @GetMapping("/list")
    public Result<?> list(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) Long shopId,
                          @RequestParam(required = false) String reservationStatus,
                          HttpServletRequest request) {
        AuthUtils.requireManager((String) request.getAttribute("role"));
        return Result.success(seatService.page(pageNum, pageSize, shopId, reservationStatus));
    }

    @GetMapping("/shop/{shopId}")
    public Result<?> listByShop(@PathVariable Long shopId) {
        return Result.success(seatService.listByShop(shopId));
    }

    @PostMapping("/save")
    public Result<?> save(@RequestBody SeatInfo seat, HttpServletRequest request) {
        AuthUtils.requireManager((String) request.getAttribute("role"));
        seatService.save(seat);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, HttpServletRequest request) {
        AuthUtils.requireManager((String) request.getAttribute("role"));
        seatService.deleteById(id);
        return Result.success();
    }
}
