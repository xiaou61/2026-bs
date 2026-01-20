package com.xiaou.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.common.Result;
import com.xiaou.entity.*;
import com.xiaou.mapper.*;
import com.xiaou.service.RecycleOrderService;
import com.xiaou.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Tag(name = "管理员接口")
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final RecycleOrderService orderService;
    private final CommunityMapper communityMapper;
    private final GarbageCategoryMapper categoryMapper;
    private final PointsProductMapper productMapper;
    private final KnowledgeMapper knowledgeMapper;
    private final NoticeMapper noticeMapper;
    private final ExchangeRecordMapper exchangeRecordMapper;
    private final PasswordEncoder passwordEncoder;

    @Operation(summary = "数据统计")
    @GetMapping("/statistics")
    public Result<Map<String, Object>> statistics() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("userCount", userService.count(new LambdaQueryWrapper<User>().eq(User::getRole, 0)));
        stats.put("collectorCount", userService.count(new LambdaQueryWrapper<User>().eq(User::getRole, 1)));
        stats.putAll(orderService.getStatistics());
        return Result.success(stats);
    }

    @Operation(summary = "订单列表")
    @GetMapping("/orders")
    public Result<IPage<RecycleOrder>> orders(@RequestParam(defaultValue = "1") Integer current,
                                              @RequestParam(defaultValue = "10") Integer size,
                                              @RequestParam(required = false) Integer status,
                                              @RequestParam(required = false) Long communityId) {
        return Result.success(orderService.pageList(current, size, status, communityId));
    }

    @Operation(summary = "用户列表")
    @GetMapping("/users")
    public Result<IPage<User>> users(@RequestParam(defaultValue = "1") Integer current,
                                     @RequestParam(defaultValue = "10") Integer size,
                                     @RequestParam(required = false) Integer role) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        if (role != null) {
            wrapper.eq(User::getRole, role);
        }
        wrapper.orderByDesc(User::getCreateTime);
        return Result.success(userService.page(new Page<>(current, size), wrapper));
    }

    @Operation(summary = "添加用户")
    @PostMapping("/user")
    public Result<Void> addUser(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus(1);
        userService.save(user);
        return Result.success();
    }

    @Operation(summary = "小区列表")
    @GetMapping("/communities")
    public Result<IPage<Community>> communities(@RequestParam(defaultValue = "1") Integer current,
                                                @RequestParam(defaultValue = "10") Integer size) {
        return Result.success(communityMapper.selectPage(new Page<>(current, size), null));
    }

    @Operation(summary = "添加小区")
    @PostMapping("/community")
    public Result<Void> addCommunity(@RequestBody Community community) {
        community.setStatus(1);
        communityMapper.insert(community);
        return Result.success();
    }

    @Operation(summary = "垃圾分类列表")
    @GetMapping("/categories")
    public Result<IPage<GarbageCategory>> categories(@RequestParam(defaultValue = "1") Integer current,
                                                     @RequestParam(defaultValue = "10") Integer size) {
        return Result.success(categoryMapper.selectPage(new Page<>(current, size),
                new LambdaQueryWrapper<GarbageCategory>().orderByAsc(GarbageCategory::getSortOrder)));
    }

    @Operation(summary = "添加/更新分类")
    @PostMapping("/category")
    public Result<Void> saveCategory(@RequestBody GarbageCategory category) {
        if (category.getId() != null) {
            categoryMapper.updateById(category);
        } else {
            category.setStatus(1);
            categoryMapper.insert(category);
        }
        return Result.success();
    }

    @Operation(summary = "积分商品列表")
    @GetMapping("/products")
    public Result<IPage<PointsProduct>> products(@RequestParam(defaultValue = "1") Integer current,
                                                 @RequestParam(defaultValue = "10") Integer size) {
        return Result.success(productMapper.selectPage(new Page<>(current, size), null));
    }

    @Operation(summary = "添加/更新商品")
    @PostMapping("/product")
    public Result<Void> saveProduct(@RequestBody PointsProduct product) {
        if (product.getId() != null) {
            productMapper.updateById(product);
        } else {
            product.setStatus(1);
            product.setExchangeCount(0);
            productMapper.insert(product);
        }
        return Result.success();
    }

    @Operation(summary = "兑换记录列表")
    @GetMapping("/exchanges")
    public Result<IPage<ExchangeRecord>> exchanges(@RequestParam(defaultValue = "1") Integer current,
                                                   @RequestParam(defaultValue = "10") Integer size,
                                                   @RequestParam(required = false) Integer status) {
        LambdaQueryWrapper<ExchangeRecord> wrapper = new LambdaQueryWrapper<>();
        if (status != null) {
            wrapper.eq(ExchangeRecord::getStatus, status);
        }
        wrapper.orderByDesc(ExchangeRecord::getCreateTime);
        return Result.success(exchangeRecordMapper.selectPage(new Page<>(current, size), wrapper));
    }

    @Operation(summary = "发放兑换")
    @PostMapping("/exchange/{id}/deliver")
    public Result<Void> deliverExchange(@PathVariable Long id) {
        ExchangeRecord record = exchangeRecordMapper.selectById(id);
        if (record != null && record.getStatus() == 0) {
            record.setStatus(1);
            exchangeRecordMapper.updateById(record);
        }
        return Result.success();
    }

    @Operation(summary = "发布知识")
    @PostMapping("/knowledge")
    public Result<Void> publishKnowledge(@RequestBody Knowledge knowledge) {
        if (knowledge.getId() != null) {
            knowledgeMapper.updateById(knowledge);
        } else {
            knowledge.setStatus(1);
            knowledge.setViewCount(0);
            knowledge.setPublishTime(LocalDateTime.now());
            knowledgeMapper.insert(knowledge);
        }
        return Result.success();
    }

    @Operation(summary = "发布公告")
    @PostMapping("/notice")
    public Result<Void> publishNotice(@RequestBody Notice notice) {
        if (notice.getId() != null) {
            noticeMapper.updateById(notice);
        } else {
            notice.setStatus(1);
            notice.setPublishTime(LocalDateTime.now());
            noticeMapper.insert(notice);
        }
        return Result.success();
    }
}
