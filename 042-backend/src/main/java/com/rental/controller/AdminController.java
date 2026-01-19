package com.rental.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rental.common.BusinessException;
import com.rental.common.Result;
import com.rental.entity.*;
import com.rental.mapper.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 管理员控制器
 */
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private HouseMapper houseMapper;

    @Autowired
    private ContractMapper contractMapper;

    @Autowired
    private BillMapper billMapper;

    @Autowired
    private AppointmentMapper appointmentMapper;

    @Autowired
    private RepairMapper repairMapper;

    private void checkAdmin(HttpServletRequest request) {
        String role = (String) request.getAttribute("role");
        if (!"admin".equals(role)) {
            throw new BusinessException("无权限访问");
        }
    }

    /**
     * 获取系统统计数据
     */
    @GetMapping("/statistics")
    public Result<Map<String, Object>> getStatistics(HttpServletRequest request) {
        checkAdmin(request);
        Map<String, Object> stats = new HashMap<>();

        // 用户统计
        stats.put("totalUsers", userMapper.selectCount(null));
        stats.put("landlordCount", userMapper.selectCount(new LambdaQueryWrapper<User>().eq(User::getRole, "landlord")));
        stats.put("tenantCount", userMapper.selectCount(new LambdaQueryWrapper<User>().eq(User::getRole, "tenant")));

        // 房源统计
        stats.put("totalHouses", houseMapper.selectCount(null));
        stats.put("rentedHouses", houseMapper.selectCount(new LambdaQueryWrapper<House>().eq(House::getStatus, 2)));
        stats.put("availableHouses", houseMapper.selectCount(new LambdaQueryWrapper<House>().eq(House::getStatus, 1)));

        // 合同统计
        stats.put("totalContracts", contractMapper.selectCount(null));
        stats.put("activeContracts", contractMapper.selectCount(new LambdaQueryWrapper<Contract>().eq(Contract::getStatus, 1)));

        // 账单统计
        List<Bill> bills = billMapper.selectList(new LambdaQueryWrapper<Bill>().eq(Bill::getStatus, 1));
        BigDecimal totalIncome = bills.stream().map(Bill::getPaidAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        stats.put("totalIncome", totalIncome);

        // 今日新增
        LocalDateTime todayStart = LocalDate.now().atStartOfDay();
        stats.put("todayUsers", userMapper.selectCount(new LambdaQueryWrapper<User>().ge(User::getCreateTime, todayStart)));
        stats.put("todayHouses", houseMapper.selectCount(new LambdaQueryWrapper<House>().ge(House::getCreateTime, todayStart)));
        stats.put("todayAppointments", appointmentMapper.selectCount(new LambdaQueryWrapper<Appointment>().ge(Appointment::getCreateTime, todayStart)));

        return Result.success(stats);
    }

    /**
     * 获取用户列表
     */
    @GetMapping("/users")
    public Result<IPage<User>> getUserList(
            HttpServletRequest request,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String role,
            @RequestParam(required = false) String keyword) {
        checkAdmin(request);
        
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        if (role != null && !role.isEmpty()) {
            wrapper.eq(User::getRole, role);
        }
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.and(w -> w.like(User::getUsername, keyword)
                    .or().like(User::getRealName, keyword)
                    .or().like(User::getPhone, keyword));
        }
        wrapper.orderByDesc(User::getCreateTime);

        IPage<User> result = userMapper.selectPage(new Page<>(page, size), wrapper);
        result.getRecords().forEach(u -> u.setPassword(null));
        return Result.success(result);
    }

    /**
     * 禁用/启用用户
     */
    @PutMapping("/user/{id}/status")
    public Result<?> updateUserStatus(
            HttpServletRequest request,
            @PathVariable Long id,
            @RequestBody Map<String, Integer> body) {
        checkAdmin(request);
        
        User user = userMapper.selectById(id);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        user.setStatus(body.get("status"));
        userMapper.updateById(user);
        return Result.success("操作成功");
    }

    /**
     * 获取房源列表(管理员)
     */
    @GetMapping("/houses")
    public Result<IPage<House>> getHouseList(
            HttpServletRequest request,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) String keyword) {
        checkAdmin(request);
        
        LambdaQueryWrapper<House> wrapper = new LambdaQueryWrapper<>();
        if (status != null) {
            wrapper.eq(House::getStatus, status);
        }
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.and(w -> w.like(House::getTitle, keyword)
                    .or().like(House::getAddress, keyword));
        }
        wrapper.orderByDesc(House::getCreateTime);

        return Result.success(houseMapper.selectPage(new Page<>(page, size), wrapper));
    }

    /**
     * 审核房源
     */
    @PutMapping("/house/{id}/audit")
    public Result<?> auditHouse(
            HttpServletRequest request,
            @PathVariable Long id,
            @RequestBody Map<String, Integer> body) {
        checkAdmin(request);
        
        House house = houseMapper.selectById(id);
        if (house == null) {
            throw new BusinessException("房源不存在");
        }
        house.setStatus(body.get("status"));
        houseMapper.updateById(house);
        return Result.success("审核完成");
    }

    /**
     * 获取合同列表(管理员)
     */
    @GetMapping("/contracts")
    public Result<IPage<Contract>> getContractList(
            HttpServletRequest request,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Integer status) {
        checkAdmin(request);
        
        LambdaQueryWrapper<Contract> wrapper = new LambdaQueryWrapper<>();
        if (status != null) {
            wrapper.eq(Contract::getStatus, status);
        }
        wrapper.orderByDesc(Contract::getCreateTime);

        return Result.success(contractMapper.selectPage(new Page<>(page, size), wrapper));
    }

    /**
     * 获取报修列表(管理员)
     */
    @GetMapping("/repairs")
    public Result<IPage<Repair>> getRepairList(
            HttpServletRequest request,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Integer status) {
        checkAdmin(request);
        
        LambdaQueryWrapper<Repair> wrapper = new LambdaQueryWrapper<>();
        if (status != null) {
            wrapper.eq(Repair::getStatus, status);
        }
        wrapper.orderByDesc(Repair::getCreateTime);

        return Result.success(repairMapper.selectPage(new Page<>(page, size), wrapper));
    }
}
