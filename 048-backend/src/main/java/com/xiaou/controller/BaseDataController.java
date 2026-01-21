package com.xiaou.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaou.common.Result;
import com.xiaou.entity.Dynasty;
import com.xiaou.entity.ExhibitionHall;
import com.xiaou.entity.RelicCategory;
import com.xiaou.entity.User;
import com.xiaou.mapper.DynastyMapper;
import com.xiaou.mapper.ExhibitionHallMapper;
import com.xiaou.mapper.RelicCategoryMapper;
import com.xiaou.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/base")
@RequiredArgsConstructor
public class BaseDataController {
    private final RelicCategoryMapper categoryMapper;
    private final DynastyMapper dynastyMapper;
    private final ExhibitionHallMapper hallMapper;
    private final UserMapper userMapper;

    @GetMapping("/categories")
    public Result<?> categories() {
        return Result.success(categoryMapper.selectList(new LambdaQueryWrapper<RelicCategory>()
                .eq(RelicCategory::getStatus, 1).orderByAsc(RelicCategory::getSort)));
    }

    @GetMapping("/dynasties")
    public Result<?> dynasties() {
        return Result.success(dynastyMapper.selectList(new LambdaQueryWrapper<Dynasty>()
                .orderByAsc(Dynasty::getSort)));
    }

    @GetMapping("/halls")
    public Result<?> halls() {
        return Result.success(hallMapper.selectList(new LambdaQueryWrapper<ExhibitionHall>()
                .eq(ExhibitionHall::getStatus, 1).orderByAsc(ExhibitionHall::getFloor)));
    }

    @GetMapping("/guides")
    public Result<?> guides() {
        return Result.success(userMapper.selectList(new LambdaQueryWrapper<User>()
                .eq(User::getRole, 1).eq(User::getStatus, 1)));
    }
}
