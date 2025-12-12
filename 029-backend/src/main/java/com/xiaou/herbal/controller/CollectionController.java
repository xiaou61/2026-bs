package com.xiaou.herbal.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaou.herbal.common.Result;
import com.xiaou.herbal.entity.Collection;
import com.xiaou.herbal.mapper.CollectionMapper;
import com.xiaou.herbal.util.UserContext;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/collection")
@Validated
public class CollectionController {

    private final CollectionMapper collectionMapper;

    public CollectionController(CollectionMapper collectionMapper) {
        this.collectionMapper = collectionMapper;
    }

    @GetMapping("/my-collections")
    public Result<List<Collection>> getMyCollections(@RequestParam(required = false) Integer targetType) {
        Long userId = UserContext.getUserId();
        if (userId == null) {
            return Result.error(401, "未授权");
        }
        try {
            LambdaQueryWrapper<Collection> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Collection::getUserId, userId);
            if (targetType != null) {
                wrapper.eq(Collection::getTargetType, targetType);
            }
            wrapper.orderByDesc(Collection::getCreateTime);
            List<Collection> collections = collectionMapper.selectList(wrapper);
            return Result.success(collections);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteCollection(@PathVariable Long id) {
        Long userId = UserContext.getUserId();
        if (userId == null) {
            return Result.error(401, "未授权");
        }
        try {
            collectionMapper.deleteById(id);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
