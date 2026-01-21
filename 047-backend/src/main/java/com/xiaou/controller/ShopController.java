package com.xiaou.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaou.common.Result;
import com.xiaou.entity.Room;
import com.xiaou.entity.ShopScript;
import com.xiaou.mapper.RoomMapper;
import com.xiaou.mapper.ShopScriptMapper;
import com.xiaou.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shop")
@RequiredArgsConstructor
public class ShopController {

    private final ShopService shopService;
    private final RoomMapper roomMapper;
    private final ShopScriptMapper shopScriptMapper;

    @GetMapping("/list")
    public Result list(@RequestParam(defaultValue = "1") int current,
                       @RequestParam(defaultValue = "10") int size,
                       @RequestParam(required = false) String keyword) {
        return Result.success(shopService.pageShops(current, size, keyword));
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        return Result.success(shopService.getById(id));
    }

    @GetMapping("/{id}/rooms")
    public Result rooms(@PathVariable Long id) {
        return Result.success(roomMapper.selectList(
                new LambdaQueryWrapper<Room>()
                        .eq(Room::getShopId, id)
                        .eq(Room::getStatus, 1)));
    }

    @GetMapping("/{id}/scripts")
    public Result scripts(@PathVariable Long id) {
        return Result.success(shopScriptMapper.selectList(
                new LambdaQueryWrapper<ShopScript>()
                        .eq(ShopScript::getShopId, id)
                        .eq(ShopScript::getStatus, 1)));
    }
}
