package com.xiaou.bike.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.bike.common.Result;
import com.xiaou.bike.dto.LoginDTO;
import com.xiaou.bike.entity.*;
import com.xiaou.bike.service.*;
import com.xiaou.bike.util.UserContext;
import com.xiaou.bike.vo.LoginVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 管理端控制器
 */
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final UserService userService;
    private final BicycleService bicycleService;
    private final StationService stationService;
    private final RentalOrderService rentalOrderService;
    private final FaultReportService faultReportService;

    // ==================== 登录相关 ====================

    /**
     * 管理员登录
     */
    @PostMapping("/login")
    public Result<LoginVO> login(@RequestBody @Valid LoginDTO dto) {
        LoginVO vo = adminService.login(dto);
        return Result.success(vo);
    }

    /**
     * 获取管理员信息
     */
    @GetMapping("/info")
    public Result<Admin> getAdminInfo() {
        Long adminId = UserContext.getUserId();
        Admin admin = adminService.getAdminInfo(adminId);
        return Result.success(admin);
    }

    // ==================== 数据统计 ====================

    /**
     * 获取统计概览
     */
    @GetMapping("/stats/overview")
    public Result<Map<String, Object>> getOverview() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalUsers", userService.listUsers(1, 1, null, null).getTotal());
        stats.put("totalBikes", bicycleService.countByStatus(null));
        stats.put("availableBikes", bicycleService.countByStatus(0));
        stats.put("inUseBikes", bicycleService.countByStatus(1));
        stats.put("totalOrders", rentalOrderService.countOrders(null));
        stats.put("todayOrders", rentalOrderService.countTodayOrders());
        stats.put("pendingFaults", faultReportService.countPending());
        return Result.success(stats);
    }

    // ==================== 用户管理 ====================

    /**
     * 获取用户列表
     */
    @GetMapping("/user/list")
    public Result<Page<User>> listUsers(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer status) {
        Page<User> result = userService.listUsers(page, size, keyword, status);
        return Result.success(result);
    }

    /**
     * 更新用户状态
     */
    @PutMapping("/user/{userId}/status")
    public Result<Void> updateUserStatus(@PathVariable Long userId, @RequestParam Integer status) {
        userService.updateStatus(userId, status);
        return Result.success();
    }

    /**
     * 审核实名认证
     */
    @PutMapping("/user/{userId}/auth")
    public Result<Void> auditAuth(@PathVariable Long userId, @RequestParam Integer authStatus) {
        userService.auditAuth(userId, authStatus);
        return Result.success();
    }

    /**
     * 调整用户信用分
     */
    @PutMapping("/user/{userId}/credit")
    public Result<Void> adjustCredit(@PathVariable Long userId, @RequestParam Integer creditScore) {
        userService.adjustCredit(userId, creditScore);
        return Result.success();
    }

    // ==================== 车辆管理 ====================

    /**
     * 获取车辆列表
     */
    @GetMapping("/bike/list")
    public Result<Page<Bicycle>> listBikes(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) Long stationId) {
        Page<Bicycle> result = bicycleService.listBicycles(page, size, keyword, status, stationId);
        return Result.success(result);
    }

    /**
     * 新增车辆
     */
    @PostMapping("/bike/add")
    public Result<Void> addBike(@RequestBody Bicycle bicycle) {
        bicycleService.addBicycle(bicycle);
        return Result.success();
    }

    /**
     * 更新车辆
     */
    @PutMapping("/bike/{id}")
    public Result<Void> updateBike(@PathVariable Long id, @RequestBody Bicycle bicycle) {
        bicycle.setId(id);
        bicycleService.updateBicycle(bicycle);
        return Result.success();
    }

    /**
     * 删除车辆
     */
    @DeleteMapping("/bike/{id}")
    public Result<Void> deleteBike(@PathVariable Long id) {
        bicycleService.deleteBicycle(id);
        return Result.success();
    }

    // ==================== 停车点管理 ====================

    /**
     * 获取停车点列表
     */
    @GetMapping("/station/list")
    public Result<Page<Station>> listStations(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer status) {
        Page<Station> result = stationService.listStations(page, size, keyword, status);
        return Result.success(result);
    }

    /**
     * 新增停车点
     */
    @PostMapping("/station/add")
    public Result<Void> addStation(@RequestBody Station station) {
        stationService.addStation(station);
        return Result.success();
    }

    /**
     * 更新停车点
     */
    @PutMapping("/station/{id}")
    public Result<Void> updateStation(@PathVariable Long id, @RequestBody Station station) {
        station.setId(id);
        stationService.updateStation(station);
        return Result.success();
    }

    /**
     * 删除停车点
     */
    @DeleteMapping("/station/{id}")
    public Result<Void> deleteStation(@PathVariable Long id) {
        stationService.deleteStation(id);
        return Result.success();
    }

    // ==================== 订单管理 ====================

    /**
     * 获取订单列表
     */
    @GetMapping("/order/list")
    public Result<Page<RentalOrder>> listOrders(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String orderNo,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Integer status) {
        Page<RentalOrder> result = rentalOrderService.listOrders(page, size, orderNo, userId, status);
        return Result.success(result);
    }

    // ==================== 故障管理 ====================

    /**
     * 获取故障列表
     */
    @GetMapping("/fault/list")
    public Result<Page<FaultReport>> listFaults(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) Long bikeId) {
        Page<FaultReport> result = faultReportService.listFaults(page, size, status, bikeId);
        return Result.success(result);
    }

    /**
     * 处理故障
     */
    @PutMapping("/fault/{id}/handle")
    public Result<Void> handleFault(
            @PathVariable Long id,
            @RequestParam Integer status,
            @RequestParam(required = false) String handleResult) {
        faultReportService.handleFault(id, status, handleResult);
        return Result.success();
    }
}
