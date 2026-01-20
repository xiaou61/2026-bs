package com.xiaou.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.common.Result;
import com.xiaou.entity.*;
import com.xiaou.mapper.*;
import com.xiaou.service.BedService;
import com.xiaou.service.ElderService;
import com.xiaou.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Tag(name = "管理员接口")
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final ElderService elderService;
    private final BedService bedService;
    private final BuildingMapper buildingMapper;
    private final RoomMapper roomMapper;
    private final FeeItemMapper feeItemMapper;
    private final PasswordEncoder passwordEncoder;

    @Operation(summary = "数据统计")
    @GetMapping("/statistics")
    public Result<Map<String, Object>> statistics() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("elderCount", elderService.count(new LambdaQueryWrapper<Elder>().eq(Elder::getStatus, 1)));
        stats.put("bedStats", bedService.getBedStatistics().get(0));
        stats.put("userCount", userService.count());
        return Result.success(stats);
    }

    @Operation(summary = "员工列表")
    @GetMapping("/user/list")
    public Result<IPage<User>> userList(@RequestParam(defaultValue = "1") Integer current,
                                        @RequestParam(defaultValue = "10") Integer size,
                                        @RequestParam(required = false) Integer role) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        if (role != null) {
            wrapper.eq(User::getRole, role);
        }
        wrapper.orderByDesc(User::getCreateTime);
        return Result.success(userService.page(new Page<>(current, size), wrapper));
    }

    @Operation(summary = "添加员工")
    @PostMapping("/user")
    public Result<Void> addUser(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus(1);
        userService.save(user);
        return Result.success();
    }

    @Operation(summary = "更新员工")
    @PutMapping("/user/{id}")
    public Result<Void> updateUser(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        } else {
            user.setPassword(null);
        }
        userService.updateById(user);
        return Result.success();
    }

    @Operation(summary = "楼栋管理")
    @PostMapping("/building")
    public Result<Void> addBuilding(@RequestBody Building building) {
        building.setStatus(1);
        buildingMapper.insert(building);
        return Result.success();
    }

    @Operation(summary = "房间管理")
    @PostMapping("/room")
    public Result<Void> addRoom(@RequestBody Room room) {
        room.setStatus(1);
        roomMapper.insert(room);
        return Result.success();
    }

    @Operation(summary = "费用项目列表")
    @GetMapping("/fee/list")
    public Result<IPage<FeeItem>> feeList(@RequestParam(defaultValue = "1") Integer current,
                                          @RequestParam(defaultValue = "10") Integer size) {
        return Result.success(feeItemMapper.selectPage(new Page<>(current, size), null));
    }

    @Operation(summary = "添加费用项目")
    @PostMapping("/fee")
    public Result<Void> addFee(@RequestBody FeeItem feeItem) {
        feeItem.setStatus(1);
        feeItemMapper.insert(feeItem);
        return Result.success();
    }
}
