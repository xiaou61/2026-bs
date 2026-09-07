package com.xiaou.bike.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.bike.common.Result;
import com.xiaou.bike.dto.RentBikeDTO;
import com.xiaou.bike.dto.ReturnBikeDTO;
import com.xiaou.bike.entity.RentalOrder;
import com.xiaou.bike.service.RentalOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 租借控制器
 */
@RestController
@RequestMapping("/rental")
@RequiredArgsConstructor
public class RentalController {

    private final RentalOrderService rentalOrderService;

    /**
     * 扫码租车
     */
    @PostMapping("/start")
    public Result<RentalOrder> rentBike(@RequestBody @Valid RentBikeDTO dto) {
        RentalOrder order = rentalOrderService.rentBike(dto);
        return Result.success(order);
    }

    /**
     * 还车
     */
    @PostMapping("/end")
    public Result<RentalOrder> returnBike(@RequestBody @Valid ReturnBikeDTO dto) {
        RentalOrder order = rentalOrderService.returnBike(dto);
        return Result.success(order);
    }

    /**
     * 获取当前进行中的订单
     */
    @GetMapping("/current")
    public Result<RentalOrder> getCurrentOrder() {
        RentalOrder order = rentalOrderService.getCurrentOrder();
        return Result.success(order);
    }
}
