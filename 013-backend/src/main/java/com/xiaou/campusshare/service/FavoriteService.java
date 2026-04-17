package com.xiaou.campusshare.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.campusshare.entity.Favorite;
import com.xiaou.campusshare.entity.IdleItem;
import com.xiaou.campusshare.entity.SkillService;
import com.xiaou.campusshare.mapper.FavoriteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FavoriteService extends ServiceImpl<FavoriteMapper, Favorite> {

    @Autowired
    private IdleItemService idleItemService;

    @Autowired
    private SkillServiceService skillServiceService;

    public Favorite getFavorite(Long userId, Long targetId, String targetType) {
        LambdaQueryWrapper<Favorite> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Favorite::getUserId, userId)
                .eq(Favorite::getTargetId, targetId)
                .eq(Favorite::getTargetType, targetType)
                .last("LIMIT 1");
        return getOne(wrapper);
    }

    public boolean toggleFavorite(Long userId, Long targetId, String targetType) {
        Favorite existing = getFavorite(userId, targetId, targetType);
        if (existing != null) {
            return removeById(existing.getId());
        }

        Favorite favorite = new Favorite();
        favorite.setUserId(userId);
        favorite.setTargetId(targetId);
        favorite.setTargetType(targetType);
        return save(favorite);
    }

    public boolean isFavorited(Long userId, Long targetId, String targetType) {
        return getFavorite(userId, targetId, targetType) != null;
    }

    public List<Map<String, Object>> getFavoriteItems(Long userId, String targetType) {
        LambdaQueryWrapper<Favorite> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Favorite::getUserId, userId)
                .eq(Favorite::getTargetType, targetType)
                .orderByDesc(Favorite::getCreateTime);
        List<Favorite> favorites = list(wrapper);
        List<Map<String, Object>> result = new ArrayList<>();

        for (Favorite favorite : favorites) {
            Map<String, Object> row = new HashMap<>();
            row.put("favoriteId", favorite.getId());
            row.put("targetId", favorite.getTargetId());
            row.put("targetType", favorite.getTargetType());
            row.put("createTime", favorite.getCreateTime());

            if ("IDLE".equalsIgnoreCase(targetType)) {
                IdleItem item = idleItemService.getById(favorite.getTargetId());
                if (item == null || item.getIsDeleted() == 1) {
                    continue;
                }
                row.put("title", item.getTitle());
                row.put("category", item.getCategory());
                row.put("price", item.getDailyPrice() != null ? item.getDailyPrice() : item.getHourlyPrice());
                row.put("priceUnit", item.getDailyPrice() != null ? "/天" : "/小时");
                row.put("status", item.getStatus());
                row.put("rating", item.getRating());
            } else if ("SKILL".equalsIgnoreCase(targetType)) {
                SkillService service = skillServiceService.getById(favorite.getTargetId());
                if (service == null || service.getIsDeleted() == 1) {
                    continue;
                }
                row.put("title", service.getTitle());
                row.put("category", service.getCategory());
                row.put("price", service.getHourlyPrice());
                row.put("priceUnit", "/小时");
                row.put("status", service.getStatus());
                row.put("rating", service.getRating());
            } else {
                continue;
            }

            result.add(row);
        }

        return result;
    }
}
