package com.rental.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rental.common.BusinessException;
import com.rental.common.Result;
import com.rental.entity.House;
import com.rental.service.HouseService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 房源控制器
 */
@RestController
@RequestMapping("/api/house")
public class HouseController {

    @Autowired
    private HouseService houseService;

    /**
     * 发布房源
     */
    @PostMapping
    public Result<?> publish(HttpServletRequest request, @RequestBody House house) {
        Long userId = (Long) request.getAttribute("userId");
        String role = (String) request.getAttribute("role");
        if (!"landlord".equals(role)) {
            throw new BusinessException("只有房东可以发布房源");
        }
        houseService.publish(userId, house);
        return Result.success("发布成功，等待审核");
    }

    /**
     * 编辑房源
     */
    @PutMapping("/{id}")
    public Result<?> update(HttpServletRequest request, @PathVariable Long id, @RequestBody House house) {
        Long userId = (Long) request.getAttribute("userId");
        house.setId(id);
        houseService.update(userId, house);
        return Result.success("更新成功");
    }

    /**
     * 删除房源
     */
    @DeleteMapping("/{id}")
    public Result<?> delete(HttpServletRequest request, @PathVariable Long id) {
        Long userId = (Long) request.getAttribute("userId");
        houseService.delete(userId, id);
        return Result.success("删除成功");
    }

    /**
     * 获取房源列表（公开）
     */
    @GetMapping("/list")
    public Result<IPage<Map<String, Object>>> getList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String district,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) String roomType,
            @RequestParam(required = false) String keyword) {
        IPage<Map<String, Object>> result = houseService.getList(page, size, city, district, 
                minPrice, maxPrice, roomType, keyword);
        return Result.success(result);
    }

    /**
     * 获取房源详情
     */
    @GetMapping("/detail/{id}")
    public Result<Map<String, Object>> getDetail(HttpServletRequest request, @PathVariable Long id) {
        Long userId = null;
        try {
            userId = (Long) request.getAttribute("userId");
        } catch (Exception ignored) {}
        Map<String, Object> result = houseService.getDetail(id, userId);
        return Result.success(result);
    }

    /**
     * 获取我的房源（房东）
     */
    @GetMapping("/my")
    public Result<IPage<House>> getMyHouses(
            HttpServletRequest request,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Integer status) {
        Long userId = (Long) request.getAttribute("userId");
        IPage<House> result = houseService.getMyHouses(userId, page, size, status);
        return Result.success(result);
    }

    /**
     * 上架/下架房源
     */
    @PutMapping("/{id}/status")
    public Result<?> updateStatus(HttpServletRequest request, @PathVariable Long id, 
            @RequestParam Integer status) {
        Long userId = (Long) request.getAttribute("userId");
        houseService.updateStatus(userId, id, status);
        return Result.success("操作成功");
    }

    /**
     * 收藏/取消收藏
     */
    @PostMapping("/{id}/favorite")
    public Result<?> toggleFavorite(HttpServletRequest request, @PathVariable Long id) {
        Long userId = (Long) request.getAttribute("userId");
        houseService.toggleFavorite(userId, id);
        return Result.success();
    }

    /**
     * 获取收藏列表
     */
    @GetMapping("/favorites")
    public Result<IPage<Map<String, Object>>> getFavorites(
            HttpServletRequest request,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        Long userId = (Long) request.getAttribute("userId");
        IPage<Map<String, Object>> result = houseService.getFavorites(userId, page, size);
        return Result.success(result);
    }
}
