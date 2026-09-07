package com.xiaou.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaou.common.Result;
import com.xiaou.entity.Reservation;
import com.xiaou.entity.Room;
import com.xiaou.entity.Shop;
import com.xiaou.entity.ShopScript;
import com.xiaou.mapper.RoomMapper;
import com.xiaou.mapper.ShopScriptMapper;
import com.xiaou.service.ReservationService;
import com.xiaou.service.ShopService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/owner")
@RequiredArgsConstructor
public class ShopOwnerController {

    private final ShopService shopService;
    private final RoomMapper roomMapper;
    private final ShopScriptMapper shopScriptMapper;
    private final ReservationService reservationService;

    @GetMapping("/shop")
    public Result myShop(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(shopService.getMyShop(userId));
    }

    @PostMapping("/shop")
    public Result saveShop(HttpServletRequest request, @RequestBody Shop shop) {
        Long userId = (Long) request.getAttribute("userId");
        Shop existing = shopService.getMyShop(userId);
        if (existing != null) {
            shop.setId(existing.getId());
            shopService.updateById(shop);
        } else {
            shop.setOwnerId(userId);
            shop.setStatus(1);
            shopService.save(shop);
        }
        return Result.success();
    }

    @GetMapping("/rooms")
    public Result rooms(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        Shop shop = shopService.getMyShop(userId);
        if (shop == null) return Result.success(java.util.Collections.emptyList());
        return Result.success(roomMapper.selectList(new LambdaQueryWrapper<Room>().eq(Room::getShopId, shop.getId())));
    }

    @PostMapping("/room")
    public Result saveRoom(HttpServletRequest request, @RequestBody Room room) {
        Long userId = (Long) request.getAttribute("userId");
        Shop shop = shopService.getMyShop(userId);
        if (shop == null) return Result.error("请先创建店铺");
        room.setShopId(shop.getId());
        if (room.getId() != null) {
            roomMapper.updateById(room);
        } else {
            room.setStatus(1);
            roomMapper.insert(room);
        }
        return Result.success();
    }

    @GetMapping("/scripts")
    public Result scripts(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        Shop shop = shopService.getMyShop(userId);
        if (shop == null) return Result.success(java.util.Collections.emptyList());
        return Result.success(shopScriptMapper.selectList(new LambdaQueryWrapper<ShopScript>().eq(ShopScript::getShopId, shop.getId())));
    }

    @PostMapping("/script")
    public Result addScript(HttpServletRequest request, @RequestBody ShopScript ss) {
        Long userId = (Long) request.getAttribute("userId");
        Shop shop = shopService.getMyShop(userId);
        if (shop == null) return Result.error("请先创建店铺");
        ss.setShopId(shop.getId());
        ss.setPlayCount(0);
        ss.setStatus(1);
        shopScriptMapper.insert(ss);
        return Result.success();
    }

    @GetMapping("/reservations")
    public Result reservations(HttpServletRequest request,
                               @RequestParam(defaultValue = "1") int current,
                               @RequestParam(defaultValue = "10") int size,
                               @RequestParam(required = false) Integer status) {
        Long userId = (Long) request.getAttribute("userId");
        Shop shop = shopService.getMyShop(userId);
        if (shop == null) return Result.success(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>());
        return Result.success(reservationService.getShopReservations(shop.getId(), current, size, status));
    }

    @PutMapping("/reservation/{id}/confirm")
    public Result confirm(@PathVariable Long id) {
        Reservation r = reservationService.getById(id);
        if (r != null && r.getStatus() == 0) {
            r.setStatus(1);
            reservationService.updateById(r);
        }
        return Result.success();
    }

    @PutMapping("/reservation/{id}/complete")
    public Result complete(@PathVariable Long id) {
        Reservation r = reservationService.getById(id);
        if (r != null) {
            r.setStatus(3);
            reservationService.updateById(r);
        }
        return Result.success();
    }
}
