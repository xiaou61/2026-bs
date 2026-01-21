package com.xiaou.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.common.Result;
import com.xiaou.entity.*;
import com.xiaou.mapper.*;
import com.xiaou.service.ScriptService;
import com.xiaou.service.ShopService;
import com.xiaou.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final ShopService shopService;
    private final ScriptService scriptService;
    private final ScriptCategoryMapper categoryMapper;
    private final ReservationMapper reservationMapper;
    private final NoticeMapper noticeMapper;
    private final ReviewMapper reviewMapper;

    @GetMapping("/statistics")
    public Result statistics() {
        long users = userService.count(new LambdaQueryWrapper<User>().eq(User::getRole, 0));
        long shops = shopService.count();
        long scripts = scriptService.count(new LambdaQueryWrapper<Script>().eq(Script::getStatus, 1));
        long reservations = reservationMapper.selectCount(null);
        return Result.success(Map.of("users", users, "shops", shops, "scripts", scripts, "reservations", reservations));
    }

    @GetMapping("/users")
    public Result users(@RequestParam(defaultValue = "1") int current,
                        @RequestParam(defaultValue = "10") int size,
                        @RequestParam(required = false) Integer role) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        if (role != null) wrapper.eq(User::getRole, role);
        wrapper.orderByDesc(User::getCreateTime);
        return Result.success(userService.page(new Page<>(current, size), wrapper));
    }

    @PutMapping("/user/{id}/status")
    public Result updateUserStatus(@PathVariable Long id, @RequestParam Integer status) {
        User user = userService.getById(id);
        if (user != null) {
            user.setStatus(status);
            userService.updateById(user);
        }
        return Result.success();
    }

    @GetMapping("/shops")
    public Result shops(@RequestParam(defaultValue = "1") int current, @RequestParam(defaultValue = "10") int size) {
        return Result.success(shopService.page(new Page<>(current, size), new LambdaQueryWrapper<Shop>().orderByDesc(Shop::getCreateTime)));
    }

    @PutMapping("/shop/{id}/status")
    public Result updateShopStatus(@PathVariable Long id, @RequestParam Integer status) {
        Shop shop = shopService.getById(id);
        if (shop != null) {
            shop.setStatus(status);
            shopService.updateById(shop);
        }
        return Result.success();
    }

    @GetMapping("/scripts")
    public Result scripts(@RequestParam(defaultValue = "1") int current,
                          @RequestParam(defaultValue = "10") int size,
                          @RequestParam(required = false) Integer status) {
        LambdaQueryWrapper<Script> wrapper = new LambdaQueryWrapper<>();
        if (status != null) wrapper.eq(Script::getStatus, status);
        wrapper.orderByDesc(Script::getCreateTime);
        return Result.success(scriptService.page(new Page<>(current, size), wrapper));
    }

    @PutMapping("/script/{id}/audit")
    public Result auditScript(@PathVariable Long id, @RequestParam Integer status, @RequestParam(required = false) String remark) {
        Script script = scriptService.getById(id);
        if (script != null) {
            script.setStatus(status);
            script.setAuditRemark(remark);
            scriptService.updateById(script);
        }
        return Result.success();
    }

    @GetMapping("/categories")
    public Result categories() {
        return Result.success(categoryMapper.selectList(new LambdaQueryWrapper<ScriptCategory>().orderByAsc(ScriptCategory::getSort)));
    }

    @PostMapping("/category")
    public Result saveCategory(@RequestBody ScriptCategory cat) {
        if (cat.getId() != null) {
            categoryMapper.updateById(cat);
        } else {
            cat.setStatus(1);
            categoryMapper.insert(cat);
        }
        return Result.success();
    }

    @GetMapping("/notices")
    public Result notices(@RequestParam(defaultValue = "1") int current, @RequestParam(defaultValue = "10") int size) {
        return Result.success(noticeMapper.selectPage(new Page<>(current, size), new LambdaQueryWrapper<Notice>().orderByDesc(Notice::getCreateTime)));
    }

    @PostMapping("/notice")
    public Result saveNotice(@RequestBody Notice notice) {
        if (notice.getId() != null) {
            noticeMapper.updateById(notice);
        } else {
            notice.setPublishTime(LocalDateTime.now());
            notice.setStatus(1);
            noticeMapper.insert(notice);
        }
        return Result.success();
    }

    @DeleteMapping("/notice/{id}")
    public Result deleteNotice(@PathVariable Long id) {
        noticeMapper.deleteById(id);
        return Result.success();
    }
}
